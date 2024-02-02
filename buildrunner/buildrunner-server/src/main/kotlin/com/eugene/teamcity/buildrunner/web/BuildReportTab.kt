package com.eugene.teamcity.buildrunner.web

import com.eugene.teamcity.buildrunner.MyConstants
import com.intellij.openapi.util.io.StreamUtil
import jetbrains.buildServer.serverSide.BuildsManager
import jetbrains.buildServer.serverSide.SBuild
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactHolder
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactsViewMode
import jetbrains.buildServer.web.openapi.BuildTab
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.WebControllerManager
import java.io.IOException

class BuildReportTab(private var manager: WebControllerManager,
                     private var pluginDescriptor: PluginDescriptor,
                     private var buildManger: BuildsManager) :
    BuildTab("BuildReportTab",
        "Build report",
        manager,
        buildManger,
        pluginDescriptor.getPluginResourcesPath("BuildReport.jsp")) {

        private fun parseTheBuildLog(artifact : BuildArtifactHolder): String? {
            val failLog = MyConstants.failLog
            if (artifact.isAvailable) {
                try {
                    val text = StreamUtil.readText(artifact.artifact.inputStream)
                    val startIndex = text.indexOf(failLog)
                    if (startIndex == -1) {
                        return null
                    }
                    else  {
                        val extractedText = text.substring(startIndex)
                        return extractedText
                    }

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return "Error Parsing File!"
        }
    override fun fillModel(model: MutableMap<String, Any>, build: SBuild) {
        // region put file contents into the model
        val buildArtifacts = build.getArtifacts(BuildArtifactsViewMode.VIEW_DEFAULT)
        val artifact = buildArtifacts.findArtifact(MyConstants.Artifact_Name)
        val text = parseTheBuildLog(artifact)

        // Change .css and then set text
        if(text != null) {
            model["text"] = text
            model["color"] = "red"
        }
        else {
            model["color"] = "green"
        }
        // endregion
    }
}