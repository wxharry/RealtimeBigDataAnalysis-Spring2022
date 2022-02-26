import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRank {

    public static void main(String[] args) throws Exception {
        if (args.length != 3 && args.length != 2) {
            System.err.println("Usage: PageRank <input path> <output path> [<number>]");
            System.exit(-1);
        }
        String input_path = args[0].charAt(args[0].length()-1) == '/' ? args[0] : args[0]+'/';
        String output_path = input_path;
        int n = args.length == 3 ? Integer.parseInt(args[2]) : 1;

        for (int i = 0; i < n; i++) {
            input_path = output_path;
            output_path = n != 1 ? args[1] + i: args[1];

            Job job = Job.getInstance();
            job.setJarByClass(PageRank.class);
            job.setJobName("PageRank");
            job.setNumReduceTasks(1);

            FileInputFormat.addInputPath(job, new Path(input_path));
            FileOutputFormat.setOutputPath(job, new Path(output_path));

            job.setMapperClass(PageRankMapper.class);
            job.setReducerClass(PageRankReducer.class);

            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(PageRankTuple.class);

            job.waitForCompletion(true);
        }
//        System.exit(job.waitForCompletion(true) ? 0 : 1);
        System.exit(0);
    }
}
