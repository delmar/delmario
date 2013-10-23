package io.delmar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinw on 08/10/13.
 */
public class NoticeContent {

    public List<NoticeItem> ITEMS = new ArrayList<NoticeItem>();

    public static Map<String, NoticeItem> ITEM_MAP = new HashMap<String, NoticeItem>();


    public static class NoticeItem {
        public String id;
        public String title;
        public String description;
        public String content;

        public NoticeItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
