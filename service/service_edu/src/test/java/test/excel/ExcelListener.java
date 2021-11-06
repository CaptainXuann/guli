package test.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author
 */
public class ExcelListener extends AnalysisEventListener<DemoData> {
    List<DemoData> list = new ArrayList<>();
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println(demoData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap+"表头");
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
