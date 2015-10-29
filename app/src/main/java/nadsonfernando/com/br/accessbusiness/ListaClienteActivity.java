package nadsonfernando.com.br.accessbusiness;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nadsonfernando.com.br.accessbusiness.adapter.ClienteAdapter;
import nadsonfernando.com.br.accessbusiness.dao.ClienteDao;
import nadsonfernando.com.br.accessbusiness.dao.DatabaseHelper;
import nadsonfernando.com.br.accessbusiness.interfaces.RecyclerViewOnClickListenerHack;
import nadsonfernando.com.br.accessbusiness.model.Cliente;
import nadsonfernando.com.br.accessbusiness.util.DividerItemDecoration;

public class ListaClienteActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener, RecyclerViewOnClickListenerHack {

    private CoordinatorLayout coordinatorLayoutListaClientes;

    private RecyclerView listaClientes;
    private ClienteAdapter adapterClientes;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fab;

    private DatabaseHelper databaseHelper;
    private ClienteDao clienteDao;

    private MenuItem searchCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);
        coordinatorLayoutListaClientes = (CoordinatorLayout) findViewById(R.id.coordinator_lista_cliente);

        databaseHelper = new DatabaseHelper(ListaClienteActivity.this);

        setUpToolbar();
        setUpViews();

    }

    private void setUpToolbar() {
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setUpViews() {
        //lista
        configureListaCliente();

        //fab
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        //Searchview
        searchCliente = (MenuItem) findViewById(R.id.action_search);
    }

    private void configureListaCliente() {
        try {
            listaClientes = (RecyclerView) findViewById(R.id.listaClientes);
            mLayoutManager = new LinearLayoutManager(ListaClienteActivity.this);
            listaClientes.setLayoutManager(mLayoutManager);
            listaClientes.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

            listaClientes.addOnItemTouchListener(new RecyclerViewTouchListener(ListaClienteActivity.this, listaClientes, this));

            clienteDao = new ClienteDao(databaseHelper.getConnectionSource());

            List<Cliente> clientes = clienteDao.queryForAll();
            adapterClientes = new ClienteAdapter(clientes);
            adapterClientes.setRecyclerViewOnClickListenerHack(this);
            listaClientes.setAdapter(adapterClientes);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setInputType(InputType.TYPE_TEXT_VARIATION_FILTER);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE | EditorInfo.IME_FLAG_NO_FULLSCREEN);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onClick(View v) {
        showDialogCliente(null, false, -1);
    }

    public void showDialogCliente(final Cliente cliente, final boolean isAtualizar, final int position) {
        View dialogCadastroCliente = getLayoutInflater().inflate(R.layout.dialog_cadastro_cliente, null);

        final EditText txtNome = (EditText) dialogCadastroCliente.findViewById(R.id.txtNome);
        final EditText txtTelefone = (EditText) dialogCadastroCliente.findViewById(R.id.txtTelefone);
        final EditText txtEndereco = (EditText) dialogCadastroCliente.findViewById(R.id.txtEndereco);

        if(cliente != null) {
            txtNome.setText(cliente.getNome());
            txtTelefone.setText(cliente.getTelefone());
            txtEndereco.setText(cliente.getEndereco());
        }

        new MaterialDialog.Builder(this)
                .title(R.string.new_client)
                .customView(dialogCadastroCliente, true)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .autoDismiss(false)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);

                        String nome = txtNome.getText().toString();
                        String telefone = txtTelefone.getText().toString();
                        String endereco = txtEndereco.getText().toString();

                        if (nome.equals("") || nome == null) {
                            txtNome.setError("Digite o nome");
                            return;
                        }
                        if (telefone.equals("") || telefone == null) {
                            txtTelefone.setError("Digite um telefone");
                            return;
                        }

                        try {
                            clienteDao = new ClienteDao(databaseHelper.getConnectionSource());
                            Cliente c = new Cliente(nome, telefone, endereco);

                            if(isAtualizar) {
                                c = adapterClientes.getItem(position);
                                c.setEndereco(endereco);
                                c.setNome(nome);
                                c.setTelefone(telefone);

                                clienteDao.update(c);
                                adapterClientes.notifyDataSetChanged();
                                Snackbar.make(coordinatorLayoutListaClientes, "Cliente atualizado!", Snackbar.LENGTH_LONG).show();
                            }else {
                                adapterClientes.add(c);
                                clienteDao.create(c);
                                Snackbar.make(coordinatorLayoutListaClientes, "Cliente cadastrado!", Snackbar.LENGTH_LONG).show();
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        dialog.dismiss();
                    }
                })
                .build()
                .show();
    }

    @Override
    public void onClickListener(View v, int position) {
        //click
    }

    @Override
    public void onLongPressClickListener(View v, final int position) {
        View dialogOptionsCliente = getLayoutInflater().inflate(R.layout.dialog_options_cliente, null);

        ArrayAdapter<String> adapterOptionsClientes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adapterOptionsClientes.add("Informações");
        adapterOptionsClientes.add("Deletar");
        adapterOptionsClientes.add("Editar");

        final ListView listaOptionsCliente = (ListView) dialogOptionsCliente.findViewById(R.id.listaInfoClientes);
        listaOptionsCliente.setAdapter(adapterOptionsClientes);

        final MaterialDialog  materialDialogOptionCliente = new MaterialDialog.Builder(this)
                .title("Opções")
                .customView(dialogOptionsCliente, true)
                .show();

        listaOptionsCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positionOption, long id) {
                switch (positionOption) {
                    case 0:
                        break;
                    case 1:
                        try {
                            clienteDao.delete(adapterClientes.getItem(position));
                            adapterClientes.remove(position);
                            materialDialogOptionCliente.dismiss();
                            Snackbar.make(coordinatorLayoutListaClientes, "Cliente excluído!", Snackbar.LENGTH_LONG).show();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        materialDialogOptionCliente.dismiss();
                        showDialogCliente(adapterClientes.getItem(position), true, position);
                        break;
                }
            }
        });

    }

    private static class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

        private Context context;
        private GestureDetector gestureDetector;
        private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

        public RecyclerViewTouchListener(Context c, final RecyclerView rv, RecyclerViewOnClickListenerHack rvh) {
            context = c;
            recyclerViewOnClickListenerHack = rvh;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View cv = rv.findChildViewUnder(e.getX(), e.getY());

                    if(cv != null && recyclerViewOnClickListenerHack != null) {
                        recyclerViewOnClickListenerHack.onLongPressClickListener(cv, rv.getChildAdapterPosition(cv));
                    }

                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return super.onSingleTapUp(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
