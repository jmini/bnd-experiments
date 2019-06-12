package fr.jmini.bnd.semver.check;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import fr.jmini.bnd.semver.check.Library;

public class LibraryTest {

    @Test
    void testSomeLibraryMethod() throws Exception {
        Library classUnderTest = new Library();
        assertThat(classUnderTest.someLibraryMethod())
                .describedAs("someLibraryMethod should return 'true'")
                .isTrue();
    }

}
