package ca.op.lamda;

import ca.op.lamda.dao.DefaultEmailDao;
import ca.op.lamda.dao.EmailDao;
import ca.op.lamda.model.Email;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class FindEmailsFunction implements RequestHandler<Void, List<Email>> {

    private final EmailDao emailDao = DefaultEmailDao.INSTANCE;

    @Override
    public List<Email> handleRequest(Void input, Context context) {
        return emailDao.findAll();
    }
}
