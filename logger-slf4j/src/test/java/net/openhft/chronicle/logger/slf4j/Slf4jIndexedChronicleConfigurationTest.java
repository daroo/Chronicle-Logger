/*
 * Copyright 2014 Higher Frequency Trading
 *
 * http://www.higherfrequencytrading.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openhft.chronicle.logger.slf4j;

import net.openhft.chronicle.ChronicleConfig;
import net.openhft.chronicle.logger.ChronicleLog;
import net.openhft.chronicle.logger.ChronicleLogLevel;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.*;

public class Slf4jIndexedChronicleConfigurationTest extends Slf4jTestBase {

    @Test
    public void testLoadProperties() {
        final String cfgPath = System.getProperty("slf4j.chronicle.indexed.properties");
        final ChronicleLoggingConfig cfg = ChronicleLoggingConfig.load(cfgPath);

        assertEquals(
            new File(basePath(ChronicleLoggingConfig.TYPE_INDEXED, "root")),
            new File(cfg.getString(ChronicleLoggingConfig.KEY_PATH)));
        assertEquals(
            ChronicleLoggingConfig.TYPE_INDEXED,
            cfg.getString(ChronicleLoggingConfig.KEY_TYPE));
        assertEquals(
            ChronicleLoggingConfig.BINARY_MODE_FORMATTED,
            cfg.getString(ChronicleLoggingConfig.KEY_BINARY_MODE));
        assertEquals(
            ChronicleLogLevel.DEBUG.toString(),
            cfg.getString(ChronicleLoggingConfig.KEY_LEVEL).toUpperCase());
        assertEquals(
            ChronicleLog.STR_FALSE,
            cfg.getString(ChronicleLoggingConfig.KEY_SHORTNAME));
        assertEquals(
            ChronicleLog.STR_FALSE,
            cfg.getString(ChronicleLoggingConfig.KEY_APPEND));
        assertEquals(
            new File(basePath(ChronicleLoggingConfig.TYPE_INDEXED, "logger_1")),
            new File(cfg.getString("logger_1", ChronicleLoggingConfig.KEY_PATH)));
        assertEquals(
            ChronicleLogLevel.INFO.toString(),
            cfg.getString("logger_1", ChronicleLoggingConfig.KEY_LEVEL).toUpperCase());
        assertEquals(
            new File(basePath(ChronicleLoggingConfig.TYPE_INDEXED, "readwrite")),
            new File(cfg.getString("readwrite", ChronicleLoggingConfig.KEY_PATH)));
        assertEquals(
            ChronicleLogLevel.DEBUG.toString(),
            cfg.getString("readwrite", ChronicleLoggingConfig.KEY_LEVEL).toUpperCase());
    }

    @Test
    public void testLoadConfig() {
        final Properties properties = new Properties();
        properties.setProperty("slf4j.chronicle.type","indexed");
        properties.setProperty("slf4j.chronicle.cfg.indexFileCapacity","128");
        properties.setProperty("slf4j.chronicle.cfg.dataBlockSize","256");
        properties.setProperty("slf4j.chronicle.cfg.synchronousMode","true");

        final ChronicleLoggingConfig clc = ChronicleLoggingConfig.load(properties);
        assertNotNull(clc.getIndexedChronicleConfig());
        assertTrue(ChronicleConfig.DEFAULT != clc.getIndexedChronicleConfig().cfg());
        assertNull(clc.getVanillaChronicleConfig());

        final ChronicleConfig cfg = clc.getIndexedChronicleConfig().cfg();
        assertEquals(128, cfg.indexFileCapacity());
        assertEquals(256, cfg.dataBlockSize());
        assertTrue(cfg.synchronousMode());
        assertEquals(ChronicleConfig.DEFAULT.indexFileExcerpts(), cfg.indexFileExcerpts());
        assertEquals(ChronicleConfig.DEFAULT.cacheLineSize(), cfg.cacheLineSize());
    }
}
