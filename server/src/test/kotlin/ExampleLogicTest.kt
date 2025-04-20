import org.junit.Test
import kotlin.test.assertEquals

@MyTmsAnnotation("C00001")
class ExampleLogicTest {
    // C00001
    @MyTmsAnnotation("C00002")
    @Test
    fun `C00002`() {
        val sum = listOf(1, 2, 3).sum()
        assertEquals(expected = 6, actual = sum)
    }
}
