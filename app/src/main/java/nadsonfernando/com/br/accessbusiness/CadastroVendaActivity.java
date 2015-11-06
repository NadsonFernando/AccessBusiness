package nadsonfernando.com.br.accessbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import nadsonfernando.com.br.accessbusiness.adapter.VendaAdapter;
import nadsonfernando.com.br.accessbusiness.dao.ClienteDao;
import nadsonfernando.com.br.accessbusiness.dao.DatabaseHelper;
import nadsonfernando.com.br.accessbusiness.dao.VendaDao;
import nadsonfernando.com.br.accessbusiness.model.Cliente;
import nadsonfernando.com.br.accessbusiness.model.Venda;

public class CadastroVendaActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private static final int CODE_RESULT = 1;

    private CoordinatorLayout coordinatorLayoutVendas;

    private EditText txtCliente;
    private EditText txtDataVenda;
    private EditText txtValor;
    private EditText txtDescricao;

    private Cliente clienteSelecionado;
    private Date data;

    private Venda venda;
    private VendaDao vendaDao;
    private DatabaseHelper databaseHelper;
    private VendaAdapter vendaAdapter;
    private int pago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_venda);

        coordinatorLayoutVendas = (CoordinatorLayout) findViewById(R.id.cadastroVenda);

        databaseHelper = new DatabaseHelper(CadastroVendaActivity.this);
        pago = 1;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setUpViews() throws SQLException {

        txtCliente = (EditText) findViewById(R.id.txtCliente);
        txtCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroVendaActivity.this, ListaClienteActivity.class);
                intent.putExtra("isForResult", true);
                startActivityForResult(intent, CODE_RESULT);
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
        txtDescricao = (EditText) findViewById(R.id.txtDescricao);

        Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(salvarVenda());
    }

    private View.OnClickListener salvarVenda() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtValor.getText().toString().isEmpty()) {
                    txtValor.setError("Digite um valor");
                    return;
                }
                if(txtCliente.getText().toString().isEmpty()) {
                    txtCliente.setError("Selecione um cliente");
                    return;
                }
                if(txtDataVenda.getText().toString().isEmpty()) {
                    txtDataVenda.setError("Selecione uma data");
                    return;
                }
                if(txtDescricao.getText().toString().isEmpty()) {
                    txtDescricao.setError("Digite uma descrição");
                    return;
                }

                try {
                    venda = new Venda(pago, data, Double.parseDouble(txtValor.getText().toString()),
                            txtDescricao.getText().toString(), clienteSelecionado);

                    vendaDao = new VendaDao(databaseHelper.getConnectionSource());
                    vendaDao.create(venda);

                    Toast.makeText(CadastroVendaActivity.this, "Venda realizada com Sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == CODE_RESULT) {
            Bundle params = intent.getExtras();

            if(params != null) {
                clienteSelecionado = (Cliente) params.get("cliente");
                txtCliente.setText(clienteSelecionado.getNome());
                txtCliente.setError(null);
            }
       }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radio_sim:
                if (checked)
                    pago = 1;
                    break;
            case R.id.radio_nao:
                if (checked)
                    pago = 0;
                    break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dia = String.valueOf(dayOfMonth);
        String mes = String.valueOf(monthOfYear+1);
        String ano = String.valueOf(year);

        String dataString;

        if(Integer.parseInt(dia) < 9)
            dia = "0" + dia;

        if(Integer.parseInt(mes) < 9)
            mes = "0" + mes;


        dataString = dia+'/'+mes+'/'+ano;

        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        try {
            data = format.parse(dataString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        txtDataVenda.setText(dataString);
    }
}
