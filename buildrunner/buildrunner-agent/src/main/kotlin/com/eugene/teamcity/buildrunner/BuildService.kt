package com.eugene.teamcity.buildrunner

import jetbrains.buildServer.agent.runner.BuildServiceAdapter
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine

// open is like public in normal languages, lol
open class BuildService() : BuildServiceAdapter() {
    override fun makeProgramCommandLine(): SimpleProgramCommandLine {
        // Construct the command line to execute your program
        val executablePath = "cmd.exe"// Set the path to your executable
        val arguments = listOf("cmd /k echo Eugene Test")// Set the command-line arguments
        return SimpleProgramCommandLine(runnerContext, executablePath, arguments)
    }
}
