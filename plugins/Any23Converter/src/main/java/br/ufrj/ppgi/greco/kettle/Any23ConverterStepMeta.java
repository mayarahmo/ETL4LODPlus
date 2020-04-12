package br.ufrj.ppgi.greco.kettle;

import br.ufrj.ppgi.greco.kettle.sparqlupdate.SparqlUpdate.Format;
import java.util.List;
import java.util.Map;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.w3c.dom.Node;

public class Any23ConverterStepMeta
  extends BaseStepMeta
  implements StepMetaInterface
{
  public static enum Field
  {
    INPUT_DATA_FIELDNAME,  INPUT_FORMAT,  OUTPUT_DATA_FIELDNAME,  OUTPUT_FORMAT,  DEFAULT_URI;
  }
  
  public static SparqlUpdate.Format getFormatFromString(String formatName)
  {
    for (int i = 0; i < formatNames.length; i++) {
      if (formatNames[i].equals(formatName)) {
        return formats[i];
      }
    }
    return null;
  }
  
  public static String getStringFromFormat(SparqlUpdate.Format format)
  {
    for (int i = 0; i < formatNames.length; i++) {
      if (formats[i] == format) {
        return formatNames[i];
      }
    }
    return null;
  }
  
  public static String[] formatNames = { "RDF+XML", "NTriples", "Turtle" };
  public static SparqlUpdate.Format[] formats = {
    SparqlUpdate.Format.RDFXML, 
    SparqlUpdate.Format.NTRIPLES, 
    SparqlUpdate.Format.TURTLE };
  private String inputDataFieldName;
  private String inputFormat;
  private String outputDataFieldName;
  private String outputFormat;
  private String defaultUri;
  
  public Any23ConverterStepMeta()
  {
    setDefault();
  }
  
  public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev, String[] input, String[] output, RowMetaInterface info)
  {
    CheckResultInterface ok = new CheckResult(
      1, 
      "", 
      stepMeta);
    remarks.add(ok);
  }
  
  public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int copyNr, TransMeta transMeta, Trans trans)
  {
    return new Any23ConverterStep(stepMeta, stepDataInterface, copyNr, transMeta, trans);
  }
  
  public StepDataInterface getStepData()
  {
    return new Any23ConverterStepData();
  }
  
  public String getDialogClassName()
  {
    return Any23ConverterStepDialog.class.getName();
  }
  
  public void loadXML(Node stepDomNode, List<DatabaseMeta> databases, Map<String, Counter> sequenceCounters)
    throws KettleXMLException
  {
    this.inputDataFieldName = XMLHandler.getTagValue(stepDomNode, Field.INPUT_DATA_FIELDNAME.name());
    this.inputFormat = XMLHandler.getTagValue(stepDomNode, Field.INPUT_FORMAT.name());
    this.outputDataFieldName = XMLHandler.getTagValue(stepDomNode, Field.OUTPUT_DATA_FIELDNAME.name());
    this.outputFormat = XMLHandler.getTagValue(stepDomNode, Field.OUTPUT_FORMAT.name());
    this.defaultUri = XMLHandler.getTagValue(stepDomNode, Field.DEFAULT_URI.name());
  }
  
  public String getXML()
    throws KettleException
  {
    StringBuilder xml = new StringBuilder();
    xml.append(XMLHandler.addTagValue(Field.INPUT_DATA_FIELDNAME.name(), this.inputDataFieldName));
    xml.append(XMLHandler.addTagValue(Field.INPUT_FORMAT.name(), this.inputFormat));
    xml.append(XMLHandler.addTagValue(Field.OUTPUT_DATA_FIELDNAME.name(), this.outputDataFieldName));
    xml.append(XMLHandler.addTagValue(Field.OUTPUT_FORMAT.name(), this.outputFormat));
    xml.append(XMLHandler.addTagValue(Field.DEFAULT_URI.name(), this.defaultUri));
    return xml.toString();
  }
  
  public void readRep(Repository repository, ObjectId stepIdInRepository, List<DatabaseMeta> databases, Map<String, Counter> sequenceCounters)
    throws KettleException
  {
    this.inputDataFieldName = repository.getStepAttributeString(stepIdInRepository, Field.INPUT_DATA_FIELDNAME.name());
    this.inputFormat = repository.getStepAttributeString(stepIdInRepository, Field.INPUT_FORMAT.name());
    this.outputDataFieldName = repository.getStepAttributeString(stepIdInRepository, Field.OUTPUT_DATA_FIELDNAME.name());
    this.outputFormat = repository.getStepAttributeString(stepIdInRepository, Field.OUTPUT_FORMAT.name());
    this.defaultUri = repository.getStepAttributeString(stepIdInRepository, Field.DEFAULT_URI.name());
  }
  
  public void saveRep(Repository repository, ObjectId idOfTransformation, ObjectId idOfStep)
    throws KettleException
  {
    repository.saveStepAttribute(idOfTransformation, idOfStep, Field.INPUT_DATA_FIELDNAME.name(), this.inputDataFieldName);
    repository.saveStepAttribute(idOfTransformation, idOfStep, Field.INPUT_FORMAT.name(), this.inputFormat);
    repository.saveStepAttribute(idOfTransformation, idOfStep, Field.OUTPUT_DATA_FIELDNAME.name(), this.outputDataFieldName);
    repository.saveStepAttribute(idOfTransformation, idOfStep, Field.OUTPUT_FORMAT.name(), this.outputFormat);
    repository.saveStepAttribute(idOfTransformation, idOfStep, Field.DEFAULT_URI.name(), this.defaultUri);
  }
  
  public void setDefault()
  {
    this.inputDataFieldName = "";
    this.inputFormat = "";
    this.outputDataFieldName = "";
    this.outputFormat = "";
    this.defaultUri = "";
  }
  
  public void getFields(RowMetaInterface inputRowMeta, String name, RowMetaInterface[] info, StepMeta nextStep, VariableSpace space)
    throws KettleStepException
  {
    ValueMetaInterface field = null;
    
    field = new ValueMeta(this.outputDataFieldName, 2);
    field.setOrigin(name);
    inputRowMeta.addValueMeta(field);
  }
  
  public String getInputDataFieldName()
  {
    return this.inputDataFieldName;
  }
  
  public void setInputDataFieldName(String inputDataFieldName)
  {
    this.inputDataFieldName = inputDataFieldName;
  }
  
  public String getInputFormat()
  {
    return this.inputFormat;
  }
  
  public void setInputFormat(String inputFormat)
  {
    this.inputFormat = inputFormat;
  }
  
  public String getOutputDataFieldName()
  {
    return this.outputDataFieldName;
  }
  
  public void setOutputDataFieldName(String ouputDataFieldName)
  {
    this.outputDataFieldName = ouputDataFieldName;
  }
  
  public String getOutputFormat()
  {
    return this.outputFormat;
  }
  
  public void setOutputFormat(String outputFormat)
  {
    this.outputFormat = outputFormat;
  }
  
  public String getDefaultUri()
  {
    return this.defaultUri;
  }
  
  public void setDefaultUri(String defaultUri)
  {
    this.defaultUri = defaultUri;
  }
}
