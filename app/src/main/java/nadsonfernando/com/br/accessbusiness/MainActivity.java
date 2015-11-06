package nadsonfernando.com.br.accessbusiness;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nadsonfernando.com.br.accessbusiness.adapter.VendaAdapter;
import nadsonfernando.com.br.accessbusiness.dao.ClienteDao;
import nadsonfernando.com.br.accessbusiness.dao.DatabaseHelper;
import nadsonfernando.com.br.accessbusiness.dao.VendaDao;
import nadsonfernando.com.br.accessbusiness.model.Cliente;
import nadsonfernando.com.br.accessbusiness.model.Venda;
import nadsonfernando.com.br.accessbusiness.util.DividerItemDecoration;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView listaVendas;
    private VendaAdapter adapterVendas;
    private RecyclerView.LayoutManager mLayoutManager;

    private VendaDao vendaDao;
    private ClienteDao clienteDao;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = setUpToobar();
        setUpDrawer(toolbar);

        databaseHelper = new DatabaseHelper(MainActivity.this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpViews();

    }

    @Override
    protected void onResume() {
        super.onResume();
        configureListaVenda();
    }

    private void setUpViews() {
        configureListaVenda();
    }

    private void configureListaVenda() {
        try {
            listaVendas = (RecyclerView) findViewById(R.id.listaVendas);
            mLayoutManager = new LinearLayoutManager(MainActivity.this);
            listaVendas.setLayoutManager(mLayoutManager);

            vendaDao = new VendaDao(databaseHelper.getConnectionSource());
            clienteDao = new ClienteDao(databaseHelper.getConnectionSource());
            List<Venda> vendas = vendaDao.queryForAll();

            for(Venda v : vendas)
                v.setCliente(clienteDao.queryForId(v.getCliente().getId()));

            adapterVendas = new VendaAdapter(vendas);
            listaVendas.setAdapter(adapterVendas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void setUpDrawer(Toolbar t) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private Toolbar setUpToobar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CadastroVendaActivity.class));
            }
        });

        return toolbar;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_clientes) {
            startActivity(new Intent(MainActivity.this, ListaClienteActivity.class));

        } else if (id == R.id.nav_enviar_relatorio) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
