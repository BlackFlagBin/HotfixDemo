package helper

import org.gradle.internal.impldep.org.eclipse.jgit.api.Git
import org.gradle.internal.impldep.org.eclipse.jgit.diff.DiffEntry
import org.gradle.internal.impldep.org.eclipse.jgit.diff.DiffFormatter
import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectReader
import org.gradle.internal.impldep.org.eclipse.jgit.lib.Ref
import org.gradle.internal.impldep.org.eclipse.jgit.lib.Repository
import org.gradle.internal.impldep.org.eclipse.jgit.revwalk.RevCommit
import org.gradle.internal.impldep.org.eclipse.jgit.revwalk.RevTree
import org.gradle.internal.impldep.org.eclipse.jgit.revwalk.RevWalk
import org.gradle.internal.impldep.org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.internal.impldep.org.eclipse.jgit.treewalk.AbstractTreeIterator
import org.gradle.internal.impldep.org.eclipse.jgit.treewalk.CanonicalTreeParser


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

    static Repository openRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder()
        return builder
            .readEnvironment()
            .findGitDir()
            .build()
    }

    static List<String> getChangedClassFullNameListBetweenBranch(DiffEntry.ChangeType changeType) {
        Repository repository = openRepository()
        Git git = new Git(repository)

        String currentBranchName = repository.getBranch()

        AbstractTreeIterator newTreeParser = prepareTreeParser(repository, "refs/heads/${currentBranchName}")
        AbstractTreeIterator oldTreeParser = prepareTreeParser(repository, "refs/heads/master")

        List<DiffEntry> diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser).setContextLines(20).call()
        List<String> changedClassFullNameList = new ArrayList<>()
        diff.findAll {
            return (it.changeType == changeType) && it.newPath.endsWith(".java")
        }.forEach() {
            DiffFormatter formatter = new DiffFormatter(System.out)
            formatter.setRepository(repository)
            formatter.format(it)

            String tempStr = it.newPath.replaceAll(File.separator, ".")
            String resStr = tempStr.substring(tempStr.indexOf("src.main.java") + "src.main.java".length() + 1, tempStr.length() - ".java".length())
            println(resStr)
            changedClassFullNameList.add(resStr)
        }
        println(changedClassFullNameList)
        return changedClassFullNameList
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


}