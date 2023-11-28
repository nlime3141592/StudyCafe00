package deu.java002_02.study.provider.service;

import deu.java002_02.study.ni.INetworkModule;
import deu.java002_02.study.ni.INetworkService;
import deu.java002_02.study.provider.gui.SeatReserveView;

public class ReserveCancelService extends ProviderService implements INetworkService
{
	private INetworkModule m_netModule;
	private SeatReserveView seatReserveView;
	private int reservationServiceId;

	public ReserveCancelService(SeatReserveView seatReserveView, int reservationServiceId)
	{
		this.seatReserveView = seatReserveView;
		this.reservationServiceId = reservationServiceId;
	}

	@Override
	public boolean tryExecuteService()
	{
		// Write: RESERVE_CANCEL_SERVICE
		m_netModule.writeLine("RESERVE_CANCEL_SERVICE");
		
		// Write: 정수 `문자열` 1개 (예약 서비스 고유번호)
		m_netModule.writeLine(String.valueOf(reservationServiceId));
		
		// Read: 통신 결과
		String result = m_netModule.readLine();

		// 통신 결과에 따라 처리
		switch(result)
		{
		case "<SUCCESS>":
			seatReserveView.removeRow(reservationServiceId); // GUI에서 행 제거
			return true;
		case "<FAILURE>":
			return false;
		default:
			return false;
		}
	}

	@Override
	public void bindNetworkModule(INetworkModule _netModule)
	{
		m_netModule = _netModule;
	}
}
