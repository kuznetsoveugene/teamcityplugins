package com.eugene.teamcity.buildrunner.web

import com.eugene.teamcity.buildrunner.MyConstants
import com.intellij.openapi.util.io.StreamUtil
import jetbrains.buildServer.serverSide.BuildsManager
import jetbrains.buildServer.serverSide.SBuild
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

    override fun fillModel(model: MutableMap<String, Any>, build: SBuild) {
        // region put file contents into the model
        val buildArtifacts = build.getArtifacts(BuildArtifactsViewMode.VIEW_DEFAULT)
        val artifact = buildArtifacts.findArtifact(MyConstants.Log_File_Name)

        if (artifact.isAvailable) {
            try {
                val text = StreamUtil.readText(artifact.artifact.inputStream)
                model["text"] = text
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        // endregion
    }
}