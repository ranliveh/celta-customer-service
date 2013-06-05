package br.com.celta.customer.view;

import br.com.celta.customer.bus.EventManager;
import br.com.celta.customer.entity.Atendente;
import br.com.celta.customer.entity.Categoria;
import br.com.celta.customer.entity.Classificacao;
import br.com.celta.customer.entity.Cliente;
import br.com.celta.customer.exceptions.CustomerException;
import br.com.celta.customer.factory.ParentLoaderFactory;
import br.com.celta.customer.persistence.AtendenteDAO;
import br.com.celta.customer.persistence.CategoriaDAO;
import br.com.celta.customer.persistence.ClassificacaoDAO;
import br.com.celta.customer.persistence.ClienteDAO;
import br.com.celta.customer.view.actions.LocalizarAction;
import br.com.celta.customer.view.controller.LocalizarDialogController;
import br.com.celta.customer.view.dialogs.Dialogs;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;
import javax.inject.Inject;

/**
 * LocalizarManager.class
 *
 * @author Ranlive Hrysyk
 */
public class LocalizarManager {

    private static final long serialVersionUID = 1L;
    @Inject
    private FXMLLoader loader;
    @Inject
    private ResourceBundle resources;
    @Inject
    private Dialogs dialogs;
    @Inject
    private CategoriaDAO categoriaDAO;
    @Inject
    private ClassificacaoDAO classificacaoDAO;
    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private AtendenteDAO atendenteDAO;

    private Parent loadParent(LocalizarAction action) {
        final String xmlPath = MessageFormat.format(ParentLoaderFactory.FXML_PATH, "LocalizarDialog");
        final InputStream stream = ParentLoaderFactory.class.getResourceAsStream(xmlPath);

        Parent root = null;
        try {
            loader.setController(null);
            loader.setRoot(null);
            loader.setLocation(getClass().getResource(xmlPath));

            root = (Parent) loader.load(stream);
            LocalizarDialogController controller = loader.getController();
            controller.configureAction(action);
        } catch (IOException exception) {
            EventManager.fire(new CustomerException(resources.getString("exception.fileNotFound", xmlPath), exception, false));
        }

        return root;
    }

    /**
     * Abre um diálogo para buscar categorias.
     */
    public Categoria localizarCategoria(final Window owner) {
        LocalizarAction<Categoria> action = new LocalizarAction<Categoria>() {
            private Categoria categoria;

            @Override
            public String getClassTitle() {
                return resources.getString("categoria.title");
            }

            @Override
            public List<Categoria> localizar(String text) {
                if (text.trim().isEmpty()) {
                    return categoriaDAO.findAll();
                }

                return categoriaDAO.findByDescricao(text);
            }

            @Override
            public Categoria getObject() {
                return categoria;
            }

            @Override
            public void setObject(Categoria value) {
                this.categoria = value;
            }
        };

        final Parent root = loadParent(action);
        dialogs.showDialog(owner, root, null);

        return action.getObject();
    }

    /**
     * Abre um diálogo para buscar classificações.
     */
    public Classificacao localizarClassificacao(final Window owner) {
        LocalizarAction<Classificacao> action = new LocalizarAction<Classificacao>() {
            private Classificacao classificacao;

            @Override
            public String getClassTitle() {
                return resources.getString("classificacao.title");
            }

            @Override
            public List<Classificacao> localizar(String text) {
                if (text.trim().isEmpty()) {
                    return classificacaoDAO.findAll();
                }

                return classificacaoDAO.findByDescricao(text);
            }

            @Override
            public Classificacao getObject() {
                return classificacao;
            }

            @Override
            public void setObject(Classificacao value) {
                this.classificacao = value;
            }
        };

        final Parent root = loadParent(action);
        dialogs.showDialog(owner, root, null);

        return action.getObject();
    }
    
    /**
     * Abre um diálogo para buscar clientes.
     */
    public Cliente localizarCliente(final Window owner) {
        LocalizarAction<Cliente> action = new LocalizarAction<Cliente>() {
            private Cliente cliente;

            @Override
            public String getClassTitle() {
                return resources.getString("cliente.title");
            }

            @Override
            public List<Cliente> localizar(String text) {
                if (text.trim().isEmpty()) {
                    return clienteDAO.findAll();
                }

                return clienteDAO.findByNome(text);
            }

            @Override
            public Cliente getObject() {
                return cliente;
            }

            @Override
            public void setObject(Cliente value) {
                this.cliente = value;
            }
        };

        final Parent root = loadParent(action);
        dialogs.showDialog(owner, root, null);

        return action.getObject();
    }
    
    /**
     * Abre um diálogo para buscar atendentes.
     */
    public Atendente localizarAtendente(final Window owner) {
        LocalizarAction<Atendente> action = new LocalizarAction<Atendente>() {
            private Atendente atendente;

            @Override
            public String getClassTitle() {
                return resources.getString("atendente.title");
            }

            @Override
            public List<Atendente> localizar(String text) {
                if (text.trim().isEmpty()) {
                    return atendenteDAO.findAll();
                }

                return atendenteDAO.findByNome(text);
            }

            @Override
            public Atendente getObject() {
                return atendente;
            }

            @Override
            public void setObject(Atendente value) {
                this.atendente = value;
            }
        };

        final Parent root = loadParent(action);
        dialogs.showDialog(owner, root, null);

        return action.getObject();
    }
}