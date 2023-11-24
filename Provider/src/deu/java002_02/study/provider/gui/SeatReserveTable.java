package deu.java002_02.study.provider.gui;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class SeatReserveTable extends JTable
{
	private SeatReserveTableModel m_tableModel;
	
	public SeatReserveTable()
	{
		m_tableModel = new SeatReserveTableModel();
		
		m_tableModel.addColumn("���� ��ȣ");
		m_tableModel.addColumn("����� ���� ��ȣ");
		m_tableModel.addColumn("�г���");
		m_tableModel.addColumn("�̿� ���� �ð�");
		m_tableModel.addColumn("�̿� ���� �ð�");
		
		super.setModel(m_tableModel);
		super.setEnabled(true);
		super.setRowSelectionAllowed(true);
		super.getTableHeader().setReorderingAllowed(false);
		super.getTableHeader().setResizingAllowed(false);
		super.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public void addRow(int _reserveId, int _uuid, String _nickname, String _beginTime, String _endTime)
	{
		m_tableModel.addRow(new String[] {
				String.valueOf(_reserveId),
				String.valueOf(_uuid),
				_nickname,
				_beginTime,
				_endTime
				});
	}

	public void removeRow(String _resid)
	{
		for(int i = 0; i < m_tableModel.getRowCount(); ++i)
			if(m_tableModel.getValueAt(i, 0).equals(_resid))
				m_tableModel.removeRow(i);
	}
}
