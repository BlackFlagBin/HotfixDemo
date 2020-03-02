package helper

import com.android.SdkConstants
import com.android.build.api.transform.TransformInput
import javassist.CannotCompileException
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.NotFoundException
import javassist.bytecode.MethodInfo
import javassist.expr.ExprEditor
import javassist.expr.MethodCall
import org.apache.commons.io.FileUtils
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.diff.DiffEntry
import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.lib.ObjectReader
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevTree
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.treewalk.AbstractTreeIterator
import org.eclipse.jgit.treewalk.CanonicalTreeParser

import java.util.regex.Matcher

class DiffHelper {

    static void main(String[] args) {
        getChangedClassFullNameListBetweenBranch(DiffEntry.ChangeType.ADD)
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        println("----------------")
        getChangedClassFullNameListBetweenBranch(DiffEntry.ChangeType.MODIFY)
    }

    static List<String> getChangedClassFullNameListBetweenBranch(DiffEntry.ChangeType changeType, Collection<TransformInput> inputs) {
        Repository repository = openRepository()
        Git git = new Git(repository)

        String currentBranchName = repository.getBranch()

        AbstractTreeIterator newTreeParser = prepareTreeParser(repository, "refs/remotes/origin/${currentBranchName}")
        AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, "refs/remotes/origin/master")

        List<DiffEntry> diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser).setContextLines(20).call()
        List<String> changedClassFullNameList = new ArrayList<>()
        diff.findAll {
            return (it.changeType == changeType) && it.newPath.endsWith(".java")
        }.forEach {
            ByteArrayOutputStream stream = new ByteArrayOutputStream()
            DiffFormatter formatter = new DiffFormatter(stream)
            formatter.setRepository(repository)
            formatter.format(it)
            String diffStr = new String(stream.toByteArray())
            println(diffStr)

            String tempStr = it.newPath.replaceAll(File.separator, ".")
            String resStr = tempStr.substring(tempStr.indexOf("src.main.java") + "src.main.java".length() + 1, tempStr.length() - ".java".length())
            println(resStr)
            changedClassFullNameList.add(resStr)

            getMethodScopeInJavaClass(resStr, inputs)
        }
        println(changedClassFullNameList)
        return changedClassFullNameList
    }

    private static Repository openRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder()
        return builder
            .readEnvironment()
            .findGitDir()
            .build()
    }

    private static AbstractTreeIterator prepareTreeParser(Repository repository, String ref) throws IOException {
        Ref head = repository.exactRef(ref)
        RevWalk walk = new RevWalk(repository)
        RevCommit commit = walk.parseCommit(head.getObjectId())
        RevTree tree = walk.parseTree(commit.getTree().getId())

        CanonicalTreeParser treeParser = new CanonicalTreeParser()
        ObjectReader reader = repository.newObjectReader()
        treeParser.reset(reader, tree.getId())

        walk.dispose()

        return treeParser
    }

    private static void getMethodScopeInJavaClass(String className, Collection<TransformInput> inputs) {
        println("className ----- " + className)

        List<CtClass> ctClassList = toCtClasses(inputs, ClassPool.getDefault())
        CtClass ctClass = ctClassList.find { it.name = className }

        println("ctClass ----- " + ctClass.name)

        CtMethod[] declaredMethods = ctClass.getDeclaredMethods()

        println("method List ----- " + declaredMethods.toArrayString())


        for (CtMethod ctMethod : declaredMethods) {
            try {
                MethodInfo methodInfo = ctMethod.getMethodInfo()
                if (methodInfo.isMethod()) {
                    println(methodInfo.getName())
                    int lineNumber = ctMethod.getMethodInfo().getLineNumber(1)
                    println("lineNumber:" + lineNumber)
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }

    static List<CtClass> toCtClasses(Collection<TransformInput> inputs, ClassPool classPool) {
        List<String> classNames = new ArrayList<>()
        List<CtClass> allClass = new ArrayList<>()
        inputs.each {
            it.directoryInputs.each {
                def dirPath = it.file.absolutePath
                classPool.insertClassPath(it.file.absolutePath)
                FileUtils.listFiles(it.file, null, true).each {
                    if (it.absolutePath.endsWith(SdkConstants.DOT_CLASS)) {
                        def className = it.absolutePath.substring(dirPath.length() + 1, it.absolutePath.length() - SdkConstants.DOT_CLASS.length())
                            .replaceAll(Matcher.quoteReplacement(File.separator), '.')
                        classNames.add(className)
                        println("toctclass" + className)
                    }
                }
            }
        }
        classNames.each {
            try {
                allClass.add(classPool.get(it))
                println("allclass --- > " + classPool.get(it).name)
            } catch (NotFoundException e) {
                println "class not found exception class name:  $it "
                e.printStackTrace()

            }
        }

        return allClass
    }

}