package com.sqli.myApp.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sqli.commons.core.config.core.properties.ReloadableProperty;
import com.sqli.commons.core.exception.FunctionalException;
import com.sqli.commons.core.service.AbstractService;
import com.sqli.myApp.adapter.BookAdapter;
import com.sqli.myApp.dao.BookDao;
import com.sqli.myApp.exception.ExceptionCode;
import com.sqli.myApp.model.Book;
import com.sqli.myApp.model.BookDTO;
import com.sqli.myApp.model.MediaDTO;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.converter.XDocConverterException;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.document.SyntaxKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

/**
 * The Class BookService.
 */
@Service
public class BookService extends AbstractService<BookDTO> {

	/** The book dao. */
	@Autowired
	private BookDao bookDao;

	/** The book adapter. */
	@Autowired
	private BookAdapter bookAdapter;

	/** The my config. */
	@ReloadableProperty("myConfig")
	private Integer myConfig;

	@Value(value = "${template.document.path}")
	private String templateDocumentPath;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sqli.commons.core.service.AbstractService #all()
	 */
	@Override
	public List<BookDTO> all() {
		List<Book> books = bookDao.findAll();
		return bookAdapter.adaptToDTO(books);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sqli.commons.core.service.AbstractService #get(java.lang.Long)
	 */
	@Override
	public BookDTO get(Long id) {
		Book book = bookDao.findById(id);
		if (book == null) {
			throw new FunctionalException(ExceptionCode.BOOK_NOT_FOUND.getCode(), ExceptionCode.BOOK_NOT_FOUND.getMessage());
		}
		return bookAdapter.adaptToDTO(book);
	}

	/**
	 * Gets the pdf.
	 * 
	 * @param id
	 *            the id
	 * @return the pdf
	 * @throws IOException
	 * @throws XDocReportException
	 * @throws XDocConverterException
	 */
	public MediaDTO getPdf(Long id) throws XDocConverterException, XDocReportException, IOException {

		BookDTO book = this.get(id);

		InputStream in = new FileInputStream(new File(this.templateDocumentPath));
		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);
		final FieldsMetadata metadata = report.createFieldsMetadata();
		metadata.addFieldAsTextStyling("description", SyntaxKind.Html);
		IContext context = report.createContext();
		context.put("title", book.getTitle());
		context.put("description", book.getDescription());
		Options options = Options.getTo(ConverterTypeTo.PDF);
		OutputStream out = new ByteArrayOutputStream();
		report.convert(context, options, out);

		final byte[] bytes = ((ByteArrayOutputStream) out).toByteArray();
		final MediaDTO media = new MediaDTO();
		media.setContent(Base64.encodeBase64String(bytes));
		media.setName(book.getTitle() + ".pdf");

		return media;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sqli.commons.core.service.AbstractService
	 * #save(com.sqli.commons.core.service.model.IDTO)
	 */
	@Override
	public Long save(BookDTO dto) {
		Book book = bookAdapter.adaptToModel(dto);
		bookDao.createOrUpdate(book);
		return book.getPK();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sqli.commons.core.service.AbstractService
	 * #delete(java.lang.Long)
	 */
	@Override
	public void delete(Long id) {
		bookDao.remove(id);
	}

}
