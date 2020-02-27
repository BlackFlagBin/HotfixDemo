import org.gradle.internal.impldep.org.eclipse.jgit.api.Git
import org.gradle.internal.impldep.org.eclipse.jgit.api.errors.GitAPIException
import org.gradle.internal.impldep.org.eclipse.jgit.diff.DiffEntry
import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectId
import org.gradle.internal.impldep.org.eclipse.jgit.lib.ObjectReader
import org.gradle.internal.impldep.org.eclipse.jgit.lib.Repository
import org.gradle.internal.impldep.org.eclipse.jgit.treewalk.CanonicalTreeParser

public class He {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = JGitHelper.openJGitCookbookRepository()) {
            // The {tree} will return the underlying tree-id instead of the commit-id itself!
            // For a description of what the carets do see e.g. http://www.paulboxley.com/blog/2011/06/git-caret-and-tilde
            // This means we are selecting the parent of the parent of the parent of the parent of current HEAD and
            // take the tree-ish of it
            ObjectId oldHead = repository.resolve("HEAD^{tree}");
            ObjectId head = repository.resolve("HEAD{tree}");

            System.out.println("Printing diff between tree: " + oldHead + " and " + head);

            // prepare the two iterators to compute the diff between
            try (ObjectReader reader = repository.newObjectReader()) {
                CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
                oldTreeIter.reset(reader, oldHead);
                CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
                newTreeIter.reset(reader, head);

                // finally get the list of changed files
                try (Git git = new Git(repository)) {
                    List<DiffEntry> diffs = git.diff()
                        .setNewTree(newTreeIter)
                        .setOldTree(oldTreeIter)
                        .call();
                    for (DiffEntry entry : diffs) {
                        System.out.println("Entry: " + entry);
                    }
                }
            }
        }

        System.out.println("Done");
    }
}