package deu.java002_02.study.provider.gui;

import java.time.LocalDateTime;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class BlacklistTable extends JTable
{
	private BlacklistTableModel m_tableModel;
	private int m_selectedRow = -1;

	public BlacklistTable()
	{
		m_tableModel = new BlacklistTableModel();

		m_tableModel.addColumn("사용자 고유 번호");
		m_tableModel.addColumn("이름");
		m_tableModel.addColumn("차단 일시");

		super.setModel(m_tableModel);
		super.setEnabled(true);
		super.setRowSelectionAllowed(true);
		super.getTableHeader().setReorderingAllowed(false);
		super.getTableHeader().setResizingAllowed(false);
		super.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public boolean addRow(int _uuid, String _name, LocalDateTime _time)
	{
		String uuidString = getUuidString(_uuid);
		String nameString = getNameString(_name);
		String dateString = getDateString(_time);
		
		for(int i = 0; i < m_tableModel.getRowCount(); ++i)
			if(m_tableModel.getValueAt(i, 0).equals(uuidString))
				return false;
		
		m_tableModel.addRow(new String[] { uuidString, nameString, dateString} );
		return true;
	}

	public boolean removeRow(int _uuid)
	{
		String uuidString = getUuidString(_uuid);

		for(int i = 0; i < m_tableModel.getRowCount(); ++i)
		{
			if(m_tableModel.getValueAt(i, 0).equals(uuidString))
			{
				m_tableModel.removeRow(i);
				return true;
			}
		}
		
		return false;
	}

	private String getUuidString(int _uuid)
	{
		return Integer.toString(_uuid);
	}
	
	private String getNameString(String _name)
	{
		return _name;
	}
	
	private String getDateString(LocalDateTime _time)
	{
		return String.format("%d. %d. %d.", _time.getYear(), _time.getMonth().getValue(), _time.getDayOfMonth());
	}
}