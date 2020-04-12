package br.ufrj.ppgi.greco.kettle;

import br.ufrj.ppgi.greco.kettle.plugin.tools.swthelper.SwtHelper;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.core.widget.ComboVar;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

public class Any23ConverterStepDialog
  extends BaseStepDialog
  implements StepDialogInterface
{
  private Any23ConverterStepMeta input;
  private SwtHelper swthlp;
  private TextVar wInputDataFieldName;
  private ComboVar wInputFormat;
  private TextVar wOutputDataFieldName;
  private ComboVar wOutputFormat;
  private TextVar wDefaultUri;
  
  public Any23ConverterStepDialog(Shell parent, Object stepMeta, TransMeta transMeta, String stepname)
  {
    super(parent, (BaseStepMeta)stepMeta, transMeta, stepname);
    
    this.input = ((Any23ConverterStepMeta)this.baseStepMeta);
    this.swthlp = new SwtHelper(transMeta, this.props);
  }
  
  private Control buildContents(Control lastControl, ModifyListener defModListener)
  {
    Group wGroup1 = this.swthlp.appendGroup(this.shell, "Entrada", lastControl);
    
    this.wInputDataFieldName = this.swthlp.appendTextRow(wGroup1, "Nome do campo com dados:", null, defModListener);
    
    this.wInputFormat = this.swthlp.appendComboRow(wGroup1, "Formato:", this.wInputDataFieldName, defModListener);
    String[] arrayOfString1;
    int j = (arrayOfString1 = Any23ConverterStepMeta.formatNames).length;
    for (int i = 0; i < j; i++)
    {
      String item = arrayOfString1[i];
      this.wInputFormat.add(item);
    }
    this.wDefaultUri = this.swthlp.appendTextRow(wGroup1, "URI padr�o:", this.wInputFormat, defModListener);
    
    Group wGroup2 = this.swthlp.appendGroup(this.shell, "Sa�da", wGroup1);
    
    this.wOutputDataFieldName = this.swthlp.appendTextRow(wGroup2, "Nome do campo com dados:", null, defModListener);
    
    this.wOutputFormat = this.swthlp.appendComboRow(wGroup2, "Formato:", this.wOutputDataFieldName, defModListener);
    String[] arrayOfString2;
    int k = (arrayOfString2 = Any23ConverterStepMeta.formatNames).length;
    for (j = 0; j < k; j++)
    {
      String item = arrayOfString2[j];
      this.wOutputFormat.add(item);
    }
    return wGroup2;
  }
  
  private void addSelectionListenerToControls(SelectionAdapter lsDef)
  {
    this.wInputDataFieldName.addSelectionListener(lsDef);
    this.wInputFormat.addSelectionListener(lsDef);
    this.wOutputDataFieldName.addSelectionListener(lsDef);
    this.wOutputFormat.addSelectionListener(lsDef);
    this.wDefaultUri.addSelectionListener(lsDef);
  }
  
  public String open()
  {
    Shell parent = getParent();
    Display display = parent.getDisplay();
    
    this.shell = new Shell(parent, 3312);
    this.props.setLook(this.shell);
    setShellImage(this.shell, this.input);
    
    ModifyListener lsMod = new ModifyListener()
    {
      public void modifyText(ModifyEvent e)
      {
        Any23ConverterStepDialog.this.input.setChanged();
      }
    };
    boolean changed = this.input.hasChanged();
    
    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = 5;
    formLayout.marginHeight = 5;
    
    this.shell.setLayout(formLayout);
    
    this.shell.setText("Any23 Converter");
    
    int middle = this.props.getMiddlePct();
    int margin = 4;
    
    this.wlStepname = new Label(this.shell, 131072);
    this.wlStepname.setText("Nome do step");
    this.props.setLook(this.wlStepname);
    
    this.fdlStepname = new FormData();
    this.fdlStepname.left = new FormAttachment(0, 0);
    this.fdlStepname.right = new FormAttachment(middle, -margin);
    this.fdlStepname.top = new FormAttachment(0, margin);
    this.wlStepname.setLayoutData(this.fdlStepname);
    
    this.wStepname = new Text(this.shell, 18436);
    this.wStepname.setText(this.stepname);
    this.props.setLook(this.wStepname);
    
    this.wStepname.addModifyListener(lsMod);
    this.fdStepname = new FormData();
    this.fdStepname.left = new FormAttachment(middle, 0);
    this.fdStepname.top = new FormAttachment(0, margin);
    this.fdStepname.right = new FormAttachment(100, 0);
    this.wStepname.setLayoutData(this.fdStepname);
    Control lastControl = this.wStepname;
    
    lastControl = buildContents(lastControl, lsMod);
    
    this.wOK = new Button(this.shell, 8);
    this.wOK.setText("OK");
    this.wCancel = new Button(this.shell, 8);
    this.wCancel.setText("Cancelar");
    setButtonPositions(new Button[] { this.wOK, this.wCancel }, margin, lastControl);
    
    this.lsCancel = new Listener()
    {
      public void handleEvent(Event e)
      {
        Any23ConverterStepDialog.this.cancel();
      }
    };
    this.lsOK = new Listener()
    {
      public void handleEvent(Event e)
      {
        Any23ConverterStepDialog.this.ok();
      }
    };
    this.wCancel.addListener(13, this.lsCancel);
    this.wOK.addListener(13, this.lsOK);
    
    this.lsDef = new SelectionAdapter()
    {
      public void widgetDefaultSelected(SelectionEvent e)
      {
        Any23ConverterStepDialog.this.ok();
      }
    };
    this.wStepname.addSelectionListener(this.lsDef);
    addSelectionListenerToControls(this.lsDef);
    
    this.shell.addShellListener(new ShellAdapter()
    {
      public void shellClosed(ShellEvent e)
      {
        Any23ConverterStepDialog.this.cancel();
      }
    });
    getData();
    
    setSize();
    
    this.input.setChanged(changed);
    
    this.shell.open();
    while (!this.shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    return this.stepname;
  }
  
  private void getData()
  {
    this.wStepname.selectAll();
    try
    {
      this.wInputDataFieldName.setText(Const.NVL(this.input.getInputDataFieldName(), ""));
      this.wInputFormat.setText(Const.NVL(this.input.getInputFormat(), ""));
      this.wOutputDataFieldName.setText(Const.NVL(this.input.getOutputDataFieldName(), ""));
      this.wOutputFormat.setText(Const.NVL(this.input.getOutputFormat(), ""));
      this.wDefaultUri.setText(Const.NVL(this.input.getDefaultUri(), ""));
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  protected void cancel()
  {
    this.stepname = null;
    this.input.setChanged(this.changed);
    dispose();
  }
  
  protected void ok()
  {
    if (Const.isEmpty(this.wStepname.getText())) {
      return;
    }
    this.stepname = this.wStepname.getText();
    try
    {
      this.input.setInputDataFieldName(this.wInputDataFieldName.getText());
      this.input.setInputFormat(this.wInputFormat.getText());
      this.input.setOutputDataFieldName(this.wOutputDataFieldName.getText());
      this.input.setOutputFormat(this.wOutputDataFieldName.getText());
      this.input.setOutputFormat(this.wOutputFormat.getText());
      this.input.setDefaultUri(this.wDefaultUri.getText());
    }
    catch (NullPointerException localNullPointerException) {}
    dispose();
  }
}

