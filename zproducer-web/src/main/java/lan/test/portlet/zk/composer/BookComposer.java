package lan.test.portlet.zk.composer;

import lan.test.config.ApplicationContextProvider;
import lan.test.portlet.rmi.dto.BookDto;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

/**
 * Composer for books.zul
 * @author nik-lazer  31.07.2015   17:51
 */
public class BookComposer extends SelectorComposer<Window> {
	@Wire
	Listbox books;

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		final ListModelList tableModel = new ListModelList();
		tableModel.addAll(ApplicationContextProvider.getBookServiceBean().getBookRMIService().getBooks());
		books.setModel(tableModel);
		books.setItemRenderer(new ListitemRenderer<BookDto>() {
			@Override
			public void render(Listitem item, BookDto data, int index) throws Exception {
				Listcell listcell = new Listcell(data.getName());
				item.appendChild(listcell);
				listcell = new Listcell(data.getAuthor());
				item.appendChild(listcell);
				listcell = new Listcell(data.getIsbnNumber().toString());
				item.appendChild(listcell);
			}
		});
	}
}
