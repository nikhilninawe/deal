package manager.impl;

import entities.CurrencyDealCount;
import entities.Deal;
import entities.DealFile;
import entities.InvalidData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import repository.CurrencyDealCountRepository;
import repository.DealFileRepository;
import repository.DealRepository;
import repository.InvalidDataRepository;

import static org.mockito.Mockito.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Created by 14577 on 10/06/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DealManagerImplTest {

    @Mock
    DealFileRepository dealFileRepository;

    @Mock
    DealRepository dealRepository;

    @Mock
    InvalidDataRepository invalidDataRepository;

    @Mock
    CurrencyDealCountRepository currencyDealCountRepository;

    @Spy
    private final ExecutorService executor = Executors.newFixedThreadPool(1);

    @InjectMocks
    DealManagerImpl dealManager;

    @Test
    public void validFileName() throws Exception {
        String fileName = "input.csv";
        when(dealFileRepository.findByFileName(fileName)).thenReturn(null);
        assertTrue(dealManager.validFileName(fileName));

        fileName = "duplicate.csv";
        DealFile dealFile = new DealFile(fileName, null, null);
        when(dealFileRepository.findByFileName(fileName)).thenReturn(dealFile);
        assertFalse(dealManager.validFileName(fileName));
    }

    @Test(expected = Exception.class)
    public void processDuplicateFile() throws Exception {
        String fileName = "duplicate.csv";
        DealFile dealFile = new DealFile(fileName, null, null);
        when(dealFileRepository.findByFileName(fileName)).thenReturn(dealFile);
        FileInputStream inputFile = new FileInputStream(fileName);
        MockMultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputFile);
        dealManager.processFile(file);
    }

    @Test
    public void processFile() throws Exception {
        String fileName = "/valid.csv";
        when(dealFileRepository.findByFileName(fileName)).thenReturn(null);
        InputStream inputFile = getClass().getResourceAsStream(fileName);
        MockMultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputFile);
        dealManager.processFile(file);
        verify(dealFileRepository, times(2)).save((DealFile) any());
        verify(dealRepository, times(1)).save((List<Deal>) any());
        verify(currencyDealCountRepository, times(9)).save((CurrencyDealCount) any());
    }

    @Test
    public void processInvalidFile() throws Exception {
        String fileName = "/invalid.csv";
        when(dealFileRepository.findByFileName(fileName)).thenReturn(null);
        InputStream inputFile = getClass().getResourceAsStream(fileName);
        MockMultipartFile file = new MockMultipartFile("file", fileName, "multipart/form-data", inputFile);
        dealManager.processFile(file);
        verify(dealFileRepository, times(2)).save((DealFile) any());
        verify(dealRepository, times(1)).save((List<Deal>) any());
        verify(invalidDataRepository, times(2)).save((InvalidData) any());
        verify(currencyDealCountRepository, times(7)).save((CurrencyDealCount) any());
    }

}