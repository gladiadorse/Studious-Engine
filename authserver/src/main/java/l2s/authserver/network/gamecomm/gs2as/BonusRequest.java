package l2s.authserver.network.gamecomm.gs2as;


import l2s.authserver.accounts.Account;
import l2s.authserver.network.gamecomm.ReceivablePacket;

public class BonusRequest extends ReceivablePacket
{
	private String account;
	private int bonus;
	private int bonusExpire;

	@Override
	protected void readImpl()
	{
		account = readS();
		bonus = readD();
		bonusExpire = readD();
	}

	@Override
	protected void runImpl()
	{
		Account acc = new Account(account);
		acc.restore();
		acc.setBonus(bonus);
		acc.setBonusExpire(bonusExpire);
		acc.update();
	}
}
