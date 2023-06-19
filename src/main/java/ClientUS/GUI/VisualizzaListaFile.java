package ClientUS.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class VisualizzaListaFile extends JFrame{

    private JPanel topPanel,topPanel1;
    private JTable table;
    private JScrollPane scrollPane,scrollPane1;
    private String[] columnNames= new String[3];
    private String[][] dataValues=new String[3][3] ;

    JButton button = new JButton("PNG");
    JButton btn = new JButton("<");
    JButton indietro = new JButton("indietro");

    public VisualizzaListaFile(){
        setTitle("Lista FIle");
        setSize(300,300);
        topPanel= new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        columnNames=new String[] {"Column 1" , "Column 2" , "Column 3","Column 4"};
        dataValues = new String[][]{
                {"1","2",},
                {"4","5",},
                {"7","8",}};

        TableModel model=new myTableModel("owntable");
        table =new JTable();
        table.setModel(model);
        table.getColumn("Column 3").setCellRenderer(new ButtonRenderer());
        table.getColumn("Column 3").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Column 4").setCellRenderer(new ButtonRenderer_IMG());
        table.getColumn("Column 4").setCellEditor(new ButtonEditor_IMG(new JCheckBox()));
        //table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false); //colonne bloccate
        scrollPane=new JScrollPane(table);

        topPanel.add(scrollPane,BorderLayout.CENTER);
        topPanel.add(indietro,BorderLayout.NORTH);

        button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                JOptionPane.showMessageDialog(null,"Button Clicked in JTable Cell");
            }
        });

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "bella zio");
            }
        });
        indietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "se cerco lo vedo");
            }
        });
    }

    class ButtonEditor extends DefaultCellEditor {
        private String label;
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
            label = (value == null) ? "PNG" : value.toString();
            button.setText(label);
            return button;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }

    }
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "PNG" : value.toString());
            return this;
        }
    }

    //----------------------------------------------------------------
    //---------------------------------------------------------------

    class ButtonEditor_IMG extends DefaultCellEditor {
        private String label;
        public ButtonEditor_IMG(JCheckBox checkBox) {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,boolean isSelected, int row, int column) {
            label = (value == null) ? "code" : value.toString();
            btn.setText(label);
            return btn;
        }

        public Object getCellEditorValue() {
            return new String(label);
        }

    }
    class ButtonRenderer_IMG extends JButton implements TableCellRenderer {
        public ButtonRenderer_IMG() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "code" : value.toString());
            return this;
        }
    }



    public class myTableModel extends DefaultTableModel{
        String dat;
        JButton button=new JButton("");

        myTableModel(String tname){
            super(dataValues,columnNames);
            dat=tname;
        }

        public boolean isCellEditable(int row,int cols){
            if( dat=="owntable"){
                if(cols==0){return false;}
            }
            return true;
        }
    }

   /* public static void main(String[] args){
        VisualizzaListaFile mainFrame=new VisualizzaListaFile();
        mainFrame.setVisible(true);

    }*/

}

