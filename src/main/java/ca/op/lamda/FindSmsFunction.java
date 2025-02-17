package ca.op.lamda;

import ca.op.lamda.dao.DefaultSmsDao;
import ca.op.lamda.dao.SmsDao;
import ca.op.lamda.model.Sms;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class FindSmsFunction implements RequestHandler<Void, List<Sms>> {

    private final SmsDao smsDao = DefaultSmsDao.INSTANCE;

    @Override
    public List<Sms> handleRequest(Void input, Context context) {
        return smsDao.findAll();
    }
}
