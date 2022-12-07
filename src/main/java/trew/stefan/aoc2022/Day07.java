package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Day07 extends AbstractAOC {

    record FileItem(String name, Long size) {
    }

    static class Folder {

        Folder parent;
        List<Folder> folders = new ArrayList<>();
        List<FileItem> fileItems = new ArrayList<>();
        String name;


        public Folder(String s, Folder current) {
            name = s;
            parent = current;
        }

        public Folder getChild(String target) {
            for (Folder folder : folders) {
                if (folder.name.equals(target)) {
                    return folder;
                }
            }

            throw new IllegalArgumentException("Folder not found [" + target + "]");
        }

        public Long getSize() {
            var result = 0L;

            for (FileItem fileItem : fileItems) {
                result += fileItem.size;
            }

            for (Folder fileItem : folders) {
                result += fileItem.getSize();
            }

            return result;
        }
    }

    @Override
    public String runPart1() {
        var cache = getFolders();
        var count = 0L;
        for (Folder folder : cache) {
            if (folder.getSize() < 100000) {
                count += folder.getSize();
            }
        }

        return String.valueOf(count);
    }

    @Override
    public String runPart2() {
        var cache = getFolders();

        var rootSize = cache.get(0).getSize();
        var targetRequired = rootSize - 40_000_000;
        var closest = rootSize;
        for (var folder : cache) {
            if (folder.getSize() > targetRequired) {
                closest = Math.min(closest, folder.getSize());
            }
        }

        return String.valueOf(closest);
    }

    private ArrayList<Folder> getFolders() {
        var list = getStringInput("");

        var cache = new ArrayList<Folder>();

        var root = new Folder("", null);
        cache.add(root);
        var current = root;
        for (var s : list) {
            if (s.equals("$ cd /")) {
                continue;
            }

            if (s.equals("$ cd ..")) {
                current = current.parent;
            } else if (s.startsWith("$")) {

                if (s.startsWith("$ cd")) {
                    var target = s.substring(5);
                    current = current.getChild(target);
                }

            } else if (s.startsWith("dir")) {
                var temp = new Folder(s.substring(4), current);
                current.folders.add(temp);
                cache.add(temp);
            } else {
                var items = s.split(" ");
                current.fileItems.add(new FileItem(items[1], Long.valueOf(items[0])));
            }
        }
        return cache;
    }

    @Override
    public String getAnswerPart1() {
        return "1517599";
    }

    @Override
    public String getAnswerPart2() {
        return "2481982";
    }
}
