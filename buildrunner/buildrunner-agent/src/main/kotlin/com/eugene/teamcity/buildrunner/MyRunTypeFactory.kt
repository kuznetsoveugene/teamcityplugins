package com.eugene.teamcity.buildrunner

import jetbrains.buildServer.agent.AgentBuildRunnerInfo
import jetbrains.buildServer.agent.BuildAgentConfiguration
import jetbrains.buildServer.agent.runner.BuildServiceAdapter
import jetbrains.buildServer.agent.runner.CommandLineBuildServiceFactory

open class MyRunTypeFactory() : CommandLineBuildServiceFactory { // Create a factory here

    override fun createService(): BuildServiceAdapter {
        return object : BuildService() {
        }
    }

    override fun getBuildRunnerInfo(): AgentBuildRunnerInfo { // Implements runner info
        return object : AgentBuildRunnerInfo {
            override fun canRun(agentConfiguration: BuildAgentConfiguration): Boolean {
                // Add logic to determine if this runner can run in the given context
                return true
            }
            override fun getType(): String {
                return MyConstants.RUNNER_TYPE
            }
        }
    }

}