package com.eugene.teamcity.buildrunner

import com.eugene.teamcity.buildrunner.MyConstants.Companion.MESSAGE_KEY
import jetbrains.buildServer.serverSide.InvalidProperty
import jetbrains.buildServer.serverSide.PropertiesProcessor
import jetbrains.buildServer.serverSide.RunType
import jetbrains.buildServer.serverSide.RunTypeRegistry
import jetbrains.buildServer.web.openapi.PluginDescriptor


class MyRunTypeRunner(private val pluginDescriptor: PluginDescriptor,
                      registry: RunTypeRegistry) : RunType() {

    // Constructor
    init {
        registry.registerRunType(this)
    }

    // TODO: Extract this into a constants type class
    override fun getType(): String {
        return "Eugene RunType"
    }

    override fun getDisplayName(): String {
        return MyConstants.RUNNER_TYPE
    }

    override fun getDescription(): String {
        return "Eugene description of My Run Type"
    }

    override fun getRunnerPropertiesProcessor(): PropertiesProcessor {
        return PropertiesProcessor { params ->
            val errors: MutableList<InvalidProperty> = ArrayList()

            val message = params.get(MESSAGE_KEY)
            if(message == null) {
                errors.add(InvalidProperty(MESSAGE_KEY, "Should not be null!"))
            }
            else if (message.startsWith("fail", 0, false)) {
                errors.add(InvalidProperty(MESSAGE_KEY, "Should not start with fail!"))
            }

        errors
        }
    }

    override fun getDefaultRunnerProperties(): Map<String, String> {
        return emptyMap()
    }

    override fun getViewRunnerParamsJspFilePath(): String {
        return pluginDescriptor.getPluginResourcesPath("myViewRunnerSettings.jsp")
    }

    override fun getEditRunnerParamsJspFilePath(): String {
        return pluginDescriptor.getPluginResourcesPath("myEditRunnerSettings.jsp")
    }

    override fun describeParameters(parameters: MutableMap<String, String>): String {
        return "Message: '" + parameters.get(MESSAGE_KEY) + "'"
    }
}
