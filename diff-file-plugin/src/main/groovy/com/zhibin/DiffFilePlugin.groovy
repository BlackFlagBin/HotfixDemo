package com.zhibin


import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import helper.DiffHelper
import org.eclipse.jgit.diff.DiffEntry
import org.gradle.api.Plugin
import org.gradle.api.Project

class DiffFilePlugin extends Transform implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.android.registerTransform(this)
    }

    @Override
    String getName() {
        return "DiffFilePlugin"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        DiffHelper.getChangedClassFullNameListBetweenBranch(DiffEntry.ChangeType.ADD, transformInvocation.getInputs())
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        DiffHelper.getChangedClassFullNameListBetweenBranch(DiffEntry.ChangeType.MODIFY, transformInvocation.getInputs())
    }
}