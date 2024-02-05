package com.eugene.teamcity.buildrunner

import jetbrains.buildServer.RunBuildException
import jetbrains.buildServer.agent.runner.BuildServiceAdapter
import jetbrains.buildServer.agent.runner.ProgramCommandLine
import jetbrains.buildServer.agent.runner.SimpleProgramCommandLine
import jetbrains.buildServer.util.FileUtil
import java.io.File
import java.io.IOException
import java.util.HashSet
import com.eugene.teamcity.buildrunner.MyConstants.Companion.MESSAGE_KEY


open class BuildService : BuildServiceAdapter() {
    private val myFilesToDelete = HashSet<File>()

    @Throws(RunBuildException::class)
    override fun makeProgramCommandLine(): ProgramCommandLine {
        val message = runnerParameters[MESSAGE_KEY]
        val logName = MyConstants.Artifact_Name

        val scriptContent = if (message?.indexOf("error") != -1) {
            "type D:\\Test_log_TC.txt > $logName"
        } else {
            "echo 'Success: $message' > $logName"
        }

        val script = getCustomScript(scriptContent)
        return SimpleProgramCommandLine(runnerContext, script, emptyList())
    }

    @Throws(RunBuildException::class)
    private fun getCustomScript(scriptContent: String): String {
        try {
            val scriptFile = File.createTempFile("build_script", ".bat", agentTempDirectory)
            FileUtil.writeFileAndReportErrors(scriptFile, scriptContent)
            myFilesToDelete.add(scriptFile)
            return scriptFile.absolutePath
        } catch (e: IOException) {
            val exception = RunBuildException("Failed to create temporary custom script file in directory", e)
            exception.isLogStacktrace = false
            throw exception
        }
    }

    override fun afterProcessFinished() {
        super.afterProcessFinished()
        for (file in myFilesToDelete) {
            FileUtil.delete(file)
        }
        myFilesToDelete.clear()

        val message = runnerParameters[MESSAGE_KEY]
        if (message?.indexOf("error") != -1)
        {
            build.buildLogger.buildFailureDescription(MyConstants.failLog)
            build.buildLogger.buildFailureDescription("Eugene has hardcoded this to fail on purpose!")
            val statusMessage = "##teamcity[buildStatus status='FAILURE' text='${MyConstants.failLog}']"
            build.buildLogger.message(statusMessage)
        }
    }
}
