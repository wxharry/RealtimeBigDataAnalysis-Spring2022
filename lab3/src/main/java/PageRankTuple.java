import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class PageRankTuple implements Writable {
    private String outlink_targets = new String();
    private String source_page = new String();
    private float page_rank = 0;

    public String getOutlink_targets(){return outlink_targets;}

    public void setOutlink_targets(String outlink_targets) {
        this.outlink_targets = outlink_targets;
    }

    public String getSource_page() {
        return source_page;
    }

    public void setSource_page(String source_page) {
        this.source_page = source_page;
    }

    public float getPage_rank() {
        return page_rank;
    }

    public void setPage_rank(float page_rank) {
        this.page_rank = page_rank;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(outlink_targets);
        dataOutput.writeUTF(source_page);
        dataOutput.writeFloat(page_rank);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        outlink_targets = dataInput.readUTF();
        source_page = dataInput.readUTF();
        page_rank = dataInput.readFloat();
    }

    @Override
    public String toString() {
        return String.format("%s %f", outlink_targets, page_rank);
//        return "PageRankTuple{" +
//                "outlink_targets='" + outlink_targets + '\'' +
//                ", source_page='" + source_page + '\'' +
//                ", page_rank=" + page_rank +
//                '}';
    }
}

