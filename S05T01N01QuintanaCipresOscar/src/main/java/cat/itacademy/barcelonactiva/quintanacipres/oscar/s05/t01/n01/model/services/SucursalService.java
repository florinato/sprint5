package cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.domain.Sucursal;
import cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.dto.SucursalDTO;
import cat.itacademy.barcelonactiva.quintanacipres.oscar.s05.t01.n01.model.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public SucursalDTO saveSucursal(SucursalDTO sucursalDTO) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNomSucursal(sucursalDTO.getNomSucursal());
        sucursal.setPaisSucursal(sucursalDTO.getPaisSucursal());

        System.out.println("Saving Sucursal: " + sucursal.getNomSucursal() + ", " + sucursal.getPaisSucursal());

        Sucursal savedSucursal = sucursalRepository.save(sucursal);
        sucursalDTO.setPk_SucursalID(savedSucursal.getPk_SucursalID());
        return convertToDTO(savedSucursal);
    }

    public List<SucursalDTO> getAllSucursales() {
        return sucursalRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public SucursalDTO getSucursalById(int id) {
        return sucursalRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public SucursalDTO updateSucursal(SucursalDTO sucursalDTO) {
        Sucursal sucursal = sucursalRepository.findById(sucursalDTO.getPk_SucursalID()).orElseThrow();
        sucursal.setNomSucursal(sucursalDTO.getNomSucursal());
        sucursal.setPaisSucursal(sucursalDTO.getPaisSucursal());

        System.out.println("Updating Sucursal: " + sucursal.getNomSucursal() + ", " + sucursal.getPaisSucursal());

        Sucursal updatedSucursal = sucursalRepository.save(sucursal);
        return convertToDTO(updatedSucursal);
    }

    public void deleteSucursal(int id) {
        sucursalRepository.deleteById(id);
    }

    private SucursalDTO convertToDTO(Sucursal sucursal) {
        SucursalDTO sucursalDTO = new SucursalDTO();
        sucursalDTO.setPk_SucursalID(sucursal.getPk_SucursalID());
        sucursalDTO.setNomSucursal(sucursal.getNomSucursal());
        sucursalDTO.setPaisSucursal(sucursal.getPaisSucursal());
        // Asegúrate de que el tipo de sucursal se calcula correctamente
        sucursalDTO.setTipusSucursal(sucursal.getPaisSucursal());
        System.out.println("Converted to DTO: " + sucursalDTO.getNomSucursal() + ", " + sucursalDTO.getPaisSucursal());
        return sucursalDTO;
    }
}

