package mx.com.cuervo.transporte.fileentries.service.impl;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import org.osgi.service.component.annotations.Component;

import mx.com.cuervo.transporte.fileentries.service.api.FileEntriesCuervoTransporteService;

/**
 * @author Jonathan
 */
@Component(
	immediate = true,
	property = {
		// TODO enter required service properties
	},
	service = FileEntriesCuervoTransporteService.class
)
public class FileEntriesCuervoTransporteServiceImpl implements FileEntriesCuervoTransporteService {

	/**
	 * variable para la muestra de logs.
	 */
	private static Log _log = LogFactoryUtil.getLog(FileEntriesCuervoTransporteServiceImpl.class);

	@Override
	public long getFileId(String folderName, String fileName, ThemeDisplay themeDisplay) {
		
		Folder folder = null;
		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), 0L, "CUERVO_TRANSPORTE");
		} catch (PortalException e) {
			_log.error(e.getCause() + "=" + e);
		}

		try {
			folder = DLAppServiceUtil.getFolder(themeDisplay.getScopeGroupId(), folder.getFolderId(), folderName);
		} catch (PortalException e) {
			_log.error(e.getCause() + "=" + e);
		}
		long fileId = 0L;

		try {
			fileId = DLAppServiceUtil.getFileEntry(themeDisplay.getScopeGroupId(), folder.getFolderId(), fileName)
					.getFileEntryId();
		} catch (PortalException e) {
			_log.error(e.getCause() + "=" + e);
		}

		return fileId;
	
	}

	public String getUrlFile(ThemeDisplay themeDisplay, DLFileEntry fileEntry) {
		
		StringBuilder urlFile = new StringBuilder((String) themeDisplay.getPortalURL());
		urlFile.append(themeDisplay.getPathContext());
		urlFile.append("/documents/");
		urlFile.append(themeDisplay.getScopeGroupId());
		urlFile.append("/");
		urlFile.append(fileEntry.getFolderId());
		urlFile.append("/");
		urlFile.append(fileEntry.getTitle());

		return urlFile.toString();
	}

}