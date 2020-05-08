package org.piyush.samples

import org.piyush.samples.paint.CommandLineRunner
import spock.lang.Specification
import spock.lang.Unroll

class CommandLineRunnerSpec extends Specification {

    @Unroll("test isValidCommand() - #commandStr")
    def "test isValidCommand"() {
        given: "a command"
        List<String> commandToTest = Arrays.asList(commandStr.split(" "));

        when: "i invoke isValidCommand()"
        def actual = CommandLineRunner.isValidCommand(commandToTest)

        then: "check result"
        actual == valid

        where:
        commandStr    | valid
        "C 20 20"     | true
        "C 1"         | false
        "C 1 1 1"     | false
        "C"           | false
        "L 1 2 3 2"   | true
        "L 1"         | false
        "L 1 3 2 3 2" | false
        "R 1 1 3 3"   | true
        "R 1 "        | false
        "R 1 2 1 2 1" | false
        "S test"      | true
        "S"           | false
        "S test test" | false
        "O test"      | true
        "O test test" | false
        "O"           | false
        "Q"           | true
        "P"           | true
        "adsfas"      | false

    }

    def "test run() method"() {
        given: "a mocked command line"
        CommandLineRunner commandLineRunner = Spy(CommandLineRunner)
        commandLineRunner.inputNextCommand() >>> [Arrays.asList("C", "20", "5"), Arrays.asList("Q"), Arrays.asList("Q")]

        when: "i run a command"
        commandLineRunner.run()

        then: "command is executed"
        //no exceptions
    }
}
