import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer
        extends Reducer<Text, PageRankTuple, Text, PageRankTuple> {
    private PageRankTuple result = new PageRankTuple();
    @Override
    public void reduce(Text key, Iterable<PageRankTuple> values, Context context)
            throws IOException, InterruptedException {
        // initialize result
        result.setOutlink_targets("");
        result.setSource_page("");
        result.setPage_rank(0);
        float pr = 0;
        String targets=new String();
//        System.out.println("Key: "+ key);
        for (PageRankTuple val: values) {
//            System.out.println("in "+val.toString());
            pr += val.getPage_rank();
            if (val.getSource_page().equals(key.toString())){
                result.setOutlink_targets(val.getOutlink_targets());
            }
        }
        result.setPage_rank(pr);
        result.setSource_page(key.toString());
//        System.out.println("out "+result.toString());
        context.write(key, result);
    }
}
