package nadsonfernando.com.br.accessbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import nadsonfernando.com.br.accessbusiness.dao.ClienteDao;
import nadsonfernando.com.br.accessbusiness.dao.DatabaseHelper;
import nadsonfernando.com.br.accessbusiness.model.Cliente;

public class CadastroVendaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final int CODE_RESULT = 1;

    private EditText txtCliente;
    private EditText txtDataVenda;
    private EditText txtValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_venda);

        setUpToolbar();
        try {
            setUpViews();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    private void setUpToolbar() {
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpViews() throws SQLException {
        txtCliente = (EditText) findViewById(R.id.txtCliente);
        txtCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CadastroVendaActivity.this, ListaClienteActivity.class), CODE_RESULT);
            }
        });

        txtDataVenda = (EditText) findViewById(R.id.txtDataVenda);
        txtDataVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        CadastroVendaActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        txtValor = (EditText) findViewById(R.id.txtValor);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == CODE_RESULT) {
            Bundle params = intent.getExtras();

            if(params != null) {
                Cliente clienteSelecionado = (Cliente) params.get("cliente");
                txtCliente.setText(clienteSelecionado.getNome());
            }
       }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dia = String.valueOf(dayOfMonth);
        String mes = String.valueOf(monthOfYear+1);
        String ano = String.valueOf(year);

        if(Integer.parseInt(dia) < 9)
            dia = "0" + dia;

        if(Integer.parseInt(mes) < 9)
            mes = "0" + mes;

        txtDataVenda.setText(dia+'/'+mes+'/'+ano);
    }
}
