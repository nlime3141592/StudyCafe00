package deu.java002_02.study.provider.gui;

import javax.swing.table.DefaultTableModel;

public class BlacklistTableModel extends DefaultTableModel
{
	@Override
	public boolean isCellEditable(int row, int column)
	{
		return false;
	}
}