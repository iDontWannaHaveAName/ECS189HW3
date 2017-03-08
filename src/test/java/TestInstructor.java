import api.IAdmin;
import api.IInstructor;
import api.IStudent;
import api.core.impl.Admin;
import api.core.impl.Instructor;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by siyangzhang on 3/6/17.
 */
public class TestInstructor {

    private IInstructor instructor;
    private IAdmin admin;
    private IStudent student;

    @Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    }

    //test addHomework()
    @Test
    public void testMakeHomework10() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        assertTrue(this.instructor.homeworkExists("TestA",2020,"Math"));
    }

    //Class is not assigned to the professor when adding homework
    @Test
    public void testMakeHomework1() {
        this.admin.createClass("TestA", 2020, "InstructorB", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        assertFalse("Class is not assigned to the professor when adding homework.",
                (this.instructor.homeworkExists("TestA",2020,"Math")));
    }

    //Homework is not assigned
    @Test
    public void testMakeHomework14() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        assertFalse("Homework is not assigned",
                (this.instructor.homeworkExists("TestA",2020,"Art")));
    }

    //Homework name cannot be empty
    @Test
    public void testMakeHomework2() {
        this.admin.createClass("TestA", 2020, "InstructorB", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "",
                "project1");
        assertFalse("Homework name cannot be empty",
                this.instructor.homeworkExists("TestA",2020,""));
    }

    //Class name cannot be empty
    @Test
    public void testMakeHomework3() {
        this.admin.createClass("TestA", 2020, "InstructorB", 15);
        this.instructor.addHomework("InstructorA", "", 2020, "Math",
                "project1");
        assertFalse("Class name cannot be empty",
                this.instructor.homeworkExists("",2020,"Math"));
    }

    //Year cannot be in the past
    @Test
    public void testMakeHomework4() {
        this.admin.createClass("TestA", 2020, "InstructorB", 15);
        this.instructor.addHomework("InstructorA", "TestA", 979, "Math",
                "project1");
        assertFalse("Year cannot be in the past",
                this.instructor.homeworkExists("TestA",979,"Math"));
    }

    //test assignGrade()
    @Test
    public void testMakeHomework5() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        this.student.registerForClass("Mike","TestA",2020);
        this.student.submitHomework("Mike","Math","MathAnswer","TestA",2020);
        this.instructor.assignGrade("InstructorA", "TestA", 2020, "Math",
                "Mike", 100);
        assertTrue((this.instructor.getGrade("TestA",2020,"Math","Mike") ==200));
    }

    //Grade cannot be less than 0
    @Test
    public void testMakeHomework6() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        this.student.registerForClass("Mike","TestA",2020);
        this.student.submitHomework("Mike","Math","MathAnswer","TestA",2020);
        this.instructor.assignGrade("InstructorA", "TestA", 2020, "Math",
                "Mike", -1);
        assertFalse("Grade cannot be less than 0",
                this.instructor.getGrade("TestA",2020,"Math",
                "Mike") == -1);
    }

    //Class name cannot be empty
    @Test
    public void testMakeHomework7() {
        this.admin.createClass("", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "", 2020, "Math",
                "project1");
        this.student.registerForClass("Mike","",2020);
        this.student.submitHomework("Mike","Math","MathAnswer","",2020);
        this.instructor.assignGrade("instructorA", "", 2020, "Math",
                "Mike", 97);
        assertFalse("Class name cannot be empty",
                this.instructor.getGrade("",2020,"Math",
                        "Mike") == 97);
    }

    //Homework name cannot be empty
    @Test
    public void testMakeHomework8() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "",
                "project1");
        this.student.registerForClass("Mike","TestA",2020);
        this.student.submitHomework("Mike","","MathAnswer","TestA",2020);
        this.instructor.assignGrade("instructorA", "TestA", 2020, "",
                "Mike", 97);
        assertFalse("Homework name cannot be empty",
                this.instructor.getGrade("TestA",2020,"",
                "Mike") == 97);
    }

    //Student name cannot be empty
    @Test
    public void testMakeHomework9() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        this.student.registerForClass("","TestA",2020);
        this.student.submitHomework("","Math","MathAnswer","TestA",2020);
        this.instructor.assignGrade("instructorA", "TestA", 2020, "Math",
                "", 97);
        assertFalse("Student name cannot be empty",
                this.instructor.getGrade("TestA",2020,"Math",
                "") == 97);
    }

    //Class is not assigned to the professor when assigning grade
    @Test
    public void testMakeHomework11() {
        this.admin.createClass("TestA", 2020, "InstructorB", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        this.student.registerForClass("Mike","TestA",2020);
        this.student.submitHomework("Mike","Math","MathAnswer","TestA",2020);
        this.instructor.assignGrade("InstructorA", "TestA", 2020, "Math",
                "Mike", 100);
        assertFalse("Class is not assigned to the professor when adding homework.",
                (this.instructor.homeworkExists("TestA",2020,"Math")) &&
                        (this.admin.getClassInstructor("TestA",2020).equals("InstructorA")) &&
                        (this.student.hasSubmitted("Mike","Math","TestA",2020) &&
                                (this.instructor.getGrade("TestA",2020,"Math","Mike") != null)));
    }

    //Homework is not assigned when assigning grade
    @Test
    public void testMakeHomework12() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Art",
                "project1");
        this.student.registerForClass("Mike","TestA",2020);
        this.student.submitHomework("Mike","Math","MathAnswer","TestA",2020);
        this.instructor.assignGrade("InstructorA", "TestA", 2020, "Math",
                "Mike", 100);
        assertFalse("Homework is not assigned when assigning grade",
                (this.instructor.homeworkExists("TestA",2020,"Math")) &&
                        (this.admin.getClassInstructor("TestA",2020).equals("InstructorA")) &&
                        (this.student.hasSubmitted("Mike","Math","TestA",2020) &&
                                (this.instructor.getGrade("TestA",2020,"Math","Mike") != null)));
    }

    //Student does not submit homework when assigning grade
    @Test
    public void testMakeHomework13() {
        this.admin.createClass("TestA", 2020, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "TestA", 2020, "Math",
                "project1");
        this.student.registerForClass("Mike","TestA",2020);
        this.student.submitHomework("Mike","Art","ArtAnswer","TestA",2020);
        this.instructor.assignGrade("InstructorA", "TestA", 2020, "Math",
                "Mike", 100);
        assertFalse("Student does not submit homework when assigning grade",
                (this.instructor.homeworkExists("TestA",2020,"Math")) &&
                        (this.admin.getClassInstructor("TestA",2020).equals("InstructorA")) &&
                        (this.student.hasSubmitted("Mike","Math","TestA",2020) &&
                                (this.instructor.getGrade("TestA",2020,"Math","Mike") != null)));
    }
}
