package model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by mobikasa on 24-12-2015.
 */
public class ModalFbFriendList
{
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<>();
    @SerializedName("paging")
    @Expose
    private Paging paging;

    /**
     *
     * @return
     * The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Datum> data) {
        this.data = data;
    }

    /**
     *
     * @return
     * The paging
     */
    public Paging getPaging() {
        return paging;
    }

    /**
     *
     * @param paging
     * The paging
     */
    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public class Cursors {

        @SerializedName("after")
        @Expose
        private String after;
        @SerializedName("before")
        @Expose
        private String before;

        /**
         *
         * @return
         * The after
         */
        public String getAfter() {
            return after;
        }

        /**
         *
         * @param after
         * The after
         */
        public void setAfter(String after) {
            this.after = after;
        }

        /**
         *
         * @return
         * The before
         */
        public String getBefore() {
            return before;
        }

        /**
         *
         * @param before
         * The before
         */
        public void setBefore(String before) {
            this.before = before;
        }

    }
    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;

        /**
         *
         * @return
         * The id
         */
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

    }
    public class Paging {

        @SerializedName("cursors")
        @Expose
        private Cursors cursors;

        /**
         *
         * @return
         * The cursors
         */
        public Cursors getCursors() {
            return cursors;
        }

        /**
         *
         * @param cursors
         * The cursors
         */
        public void setCursors(Cursors cursors) {
            this.cursors = cursors;
        }

    }
}
