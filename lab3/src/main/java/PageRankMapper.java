import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageRankMapper
        extends Mapper<LongWritable, Text,
                        Text, PageRankTuple> {
    private Text outlink_target = new Text();
    private PageRankTuple outTuple = new PageRankTuple();
    private String pattern = "([A-Z])\\s((?:[A-Z](?:\\s)*)*)\\s(\\d.\\d*)";

    @Override
    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
//      parse input
        String line = value.toString();
        Matcher m = Pattern.compile(pattern).matcher(line);
        m.find();
        String src_page = m.group(1);
        String targets = m.group(2);
        float pr = Float.parseFloat(m.group(3));
        String[] targets_split = targets.split(" ");
//      set output
//      write pairs <outlink_target>: <source_page, PR/number_of_outlinks>
        for (String target: targets_split) {
            outlink_target.set(target);
            outTuple.setSource_page(src_page);
            outTuple.setPage_rank(pr / targets_split.length);
//            System.out.println(outlink_target + ":" + outTuple.toString());
            context.write(outlink_target, outTuple);
        }
//      write pairs <source_page>: <outlink_targets>
        outlink_target.set(src_page);
        outTuple.setOutlink_targets(targets);
        outTuple.setPage_rank(0);
//        System.out.println(outlink_target + ": " + outTuple.toString());
        context.write(outlink_target, outTuple);
    }
}
