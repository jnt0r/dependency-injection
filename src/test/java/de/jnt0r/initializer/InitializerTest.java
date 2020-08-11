package de.jnt0r.initializer;

import componentScanTestPackage.ThirdComponent;
import componentScanTestPackage.subPackage1.FirstComponent;
import componentScanTestPackage.subPackage2.SecondComponent;
import de.jnt0r.initializer.exceptions.InitializationException;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import testApplications.TestApplication;
import testApplications.TestApplicationWithInvalidAutowire;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class InitializerTest {

    @Test
    public void testComponentScan() {
        Set<Class<?>> expected = new HashSet<>(Arrays.asList(FirstComponent.class, SecondComponent.class, ThirdComponent.class));
        Set<Class<?>> components = Initializer.componentScan(Package.getPackage("componentScanTestPackage"));

        assertEquals(expected, components);
    }

    @Test
    public void testAutowiringNonComponentClassThrowsException() {
        assertThrows(InitializationException.class, () -> Initializer.initialize(TestApplicationWithInvalidAutowire.class, new String[]{}));
    }

    @Test
    public void test() {
        TestApplication app = Initializer.initialize(TestApplication.class, new String[]{});
        assertEquals("it works", app.getComponentString());
    }

}
