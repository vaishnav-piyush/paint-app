package org.piyush.samples

import org.piyush.samples.paint.AsciiCanvas
import org.piyush.samples.paint.Canvas
import spock.lang.Specification
import spock.lang.Unroll

class AsciiCanvasSpec extends Specification {

    @Unroll("test line - #scenario")
    def "test line"() {
        given: "a canvas"
        Canvas canvas = new AsciiCanvas(10, 5, '*' as char, 'x' as char)

        when: "i draw a line"
        canvas.drawLine(x1, y1, x2, y2)

        and:
        def expStr = getClass().getResource(expFile).text
        print(expStr)

        then: "resulting canvas is as expected"
        canvas.toString() == expStr

        where:
        x1 | y1 | x2 | y2 | expFile                         | scenario
        3  | 2  | 8  | 2  | "/horizontal_line.txt"          | "horizontal line normal co-ordinates"
        8  | 2  | 3  | 2  | "/horizontal_line.txt"          | "horizontal line reverse co-ordinates"
        3  | 5  | 10 | 5  | "/horizontal_line_boundary.txt" | "horizontal line boundary"
        9  | 2  | 9  | 4  | "/vertical_line.txt"            | "vertical line normal co-ordinates"
        9  | 4  | 9  | 2  | "/vertical_line.txt"            | "vertical line normal co-ordinates"
        10 | 2  | 10 | 5  | "/vertical_line_boundary.txt"   | "vertical line boundary"
    }

    @Unroll("test rectangle - #scenario")
    def "test rectangle"() {
        given: "an empty canvas"
        Canvas canvas = new AsciiCanvas(8, 5, '*' as char, 'x' as char)

        when: "i draw a rectangle"
        canvas.drawRectangle(x1, y1, x2, y2)
        canvas.print()

        and:
        def expStr = getClass().getResource(expFile).text

        then: "resulting canvas is as expected"
        canvas.toString() == expStr

        where:
        x1 | y1 | x2 | y2 | expFile                   | scenario
        3  | 2  | 6  | 4  | "/rectangle.txt"          | "rectangle nomal co-ordinates"
        6  | 4  | 3  | 2  | "/rectangle.txt"          | "rectangle reverse co-ordinates"
        1  | 1  | 8  | 5  | "/boundary_rectangle.txt" | "boundary rectangle"
    }

    @Unroll("exception scenarios - #scenario")
    def "exception scenarios"() {
        given: "an empty canvas"
        Canvas canvas = new AsciiCanvas(8, 5, '*' as char, 'x' as char)

        when: "i draw an illegal line"
        canvas.drawLine(9, 5, 1, 0)

        then: "i get an exception"
        thrown(IllegalArgumentException)

        when: "i draw an illegal rectangle"
        canvas.drawRectangle(0, 0, 8, 6)

        then: "i get an exception"
        thrown(IllegalArgumentException)
    }

    def "test save operation"() {
        given: "an empty canvas"
        Canvas canvas = new AsciiCanvas(8, 5, '*' as char, 'x' as char)

        when: "i draw some shapes in it"
        canvas.drawLine(5, 2, 8, 2)
        canvas.drawRectangle(2, 2, 4, 4)

        and: "i save it to a file"
        canvas.saveToFile(getClass().getResource("/temp.txt").getPath())

        and: "read expected and actual files"
        String exp = getClass().getResource("/saved_canvas.txt").text
        String actual = getClass().getResource("/temp.txt").text

        then: "then file is same as expected"
        actual == exp
    }

    def "text open operation"() {
        given: "an empty canvas"
        Canvas canvas = new AsciiCanvas(8, 5, '*' as char, 'x' as char)

        when: "i open an existing canvas"
        canvas.readFromFile(getClass().getResource("/saved_canvas.txt").getPath())

        and:
        def expStr = getClass().getResource("/saved_canvas_str.txt").text

        then: "the canvas is same as expected"
        canvas.toString() == expStr
    }


}
