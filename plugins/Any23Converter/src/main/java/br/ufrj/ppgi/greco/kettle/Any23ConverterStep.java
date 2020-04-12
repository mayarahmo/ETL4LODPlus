package br.ufrj.ppgi.greco.kettle;

import br.ufrj.ppgi.greco.kettle.sparqlupdate.SparqlUpdate;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

public class Any23ConverterStep
  extends BaseStep
  implements StepInterface
{
  public Any23ConverterStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans)
  {
    super(stepMeta, stepDataInterface, copyNr, transMeta, trans);
  }
  
  public boolean processRow(StepMetaInterface smi, StepDataInterface sdi)
    throws KettleException
  {
    Any23ConverterStepMeta meta = (Any23ConverterStepMeta)smi;
    Any23ConverterStepData data = (Any23ConverterStepData)sdi;
    
    Object[] row = getRow();
    if (row == null)
    {
      setOutputDone();
      return false;
    }
    if (this.first)
    {
      this.first = false;
      
      RowMetaInterface rowMeta = getInputRowMeta();
      data.outputRowMeta = rowMeta.clone();
      meta.getFields(data.outputRowMeta, getStepname(), null, null, this);
    }
    String outputData = SparqlUpdate.convert(
      Any23ConverterStepMeta.getFormatFromString(meta.getInputFormat()), 
      getInputRowMeta().getString(row, meta.getInputDataFieldName(), ""), 
      meta.getDefaultUri(), 
      Any23ConverterStepMeta.getFormatFromString(meta.getOutputFormat()));
    
    Object[] outputRow = (Object[])null;
    outputRow = RowDataUtil.addValueData(row, getInputRowMeta().size(), outputData);
    putRow(data.outputRowMeta, outputRow);
    
    return true;
  }
}
