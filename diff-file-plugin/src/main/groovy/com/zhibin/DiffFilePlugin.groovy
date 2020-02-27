package com.zhibin

import org.gradle.api.Plugin
import org.gradle.api.Project

class DiffFilePlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        print("difffileplugin apply success")
    }
}