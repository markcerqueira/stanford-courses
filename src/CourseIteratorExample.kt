import edu.stanford.services.explorecourses.ExploreCoursesConnection
import org.jdom2.JDOMException
import java.io.IOException

/**
 * Prints a list of all courses offered at Stanford in the current academic year
 */
object CourseIteratorExample {
    @Throws(IOException::class, JDOMException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val connection = ExploreCoursesConnection()
        for (s in connection.schools) {
            for (d in s.departments) {
                for (c in connection.getCoursesByQuery(d.code)) println(c.subjectCodePrefix + c.subjectCodeSuffix + ": " + c.title)
            }
        }
    }
}
