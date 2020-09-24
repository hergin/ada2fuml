package model;

import model.auxiliary.IPlaceholderReplacement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    @Test
    void getAllPlaceholderReplacementsRecursively() {
        Package p1 = new Package("P1");
        Class c1 = new Class("C1");
        p1.addClass(c1);

        Package sub1 = new Package("Sub1");
        Class c2 = new Class("C2");
        sub1.addClass(c2);
        p1.addSubPackage(sub1);

        Package subSub1 = new Package("SubSub1");
        Class c3 = new Class("C3");
        subSub1.addClass(c3);
        sub1.addSubPackage(subSub1);

        List<IPlaceholderReplacement> result = Package.getAllPlaceholderReplacementsRecursively(p1);
        assertEquals(3,result.size());
    }
}