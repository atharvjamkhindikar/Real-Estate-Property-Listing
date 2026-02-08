package com.demo.real_estate_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.real_estate_backend.dto.ContactAgentRequest;
import com.demo.real_estate_backend.exception.ResourceNotFoundException;
import com.demo.real_estate_backend.model.ContactAgent;
import com.demo.real_estate_backend.model.Property;
import com.demo.real_estate_backend.model.User;
import com.demo.real_estate_backend.repository.ContactAgentRepository;
import com.demo.real_estate_backend.repository.PropertyRepository;
import com.demo.real_estate_backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactAgentService {

    @Autowired
    private ContactAgentRepository contactAgentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public ContactAgent createContact(ContactAgentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        Property property = propertyRepository.findById(request.getPropertyId())
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", request.getPropertyId()));

        ContactAgent contact = new ContactAgent();
        contact.setUser(user);
        contact.setProperty(property);
        contact.setSubject(request.getSubject());
        contact.setMessage(request.getMessage());
        contact.setSenderName(request.getSenderName());
        contact.setSenderEmail(request.getSenderEmail());
        contact.setSenderPhone(request.getSenderPhone());
        contact.setAdditionalInfo(request.getAdditionalInfo());
        contact.setIsRead(false);

        return contactAgentRepository.save(contact);
    }

    public Optional<ContactAgent> getContactById(Long id) {
        return contactAgentRepository.findById(id);
    }

    public List<ContactAgent> getContactsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", propertyId));

        return contactAgentRepository.findByPropertyIdOrderByCreatedAtDesc(propertyId);
    }

    public List<ContactAgent> getContactsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return contactAgentRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public List<ContactAgent> getContactsForPropertyOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ownerId));

        return contactAgentRepository.findAllContactsForPropertyOwner(ownerId);
    }

    public List<ContactAgent> getUnreadContacts() {
        return contactAgentRepository.findByIsReadFalse();
    }

    public List<ContactAgent> getUnreadContactsForOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ownerId));

        return contactAgentRepository.findByIsReadFalseAndPropertyOwnerId(ownerId);
    }

    public ContactAgent markAsRead(Long contactId) {
        ContactAgent contact = contactAgentRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("ContactAgent", "id", contactId));

        contact.setIsRead(true);
        contact.setRespondedAt(LocalDateTime.now());

        return contactAgentRepository.save(contact);
    }

    public void deleteContact(Long contactId) {
        if (!contactAgentRepository.existsById(contactId)) {
            throw new ResourceNotFoundException("ContactAgent", "id", contactId);
        }
        contactAgentRepository.deleteById(contactId);
    }

    public long getUnreadCountForOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", ownerId));

        return contactAgentRepository.countByIsReadFalseAndPropertyOwnerId(ownerId);
    }

    public long getContactCountForProperty(Long propertyId, LocalDateTime since) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new ResourceNotFoundException("Property", "id", propertyId));

        return contactAgentRepository.countByPropertyIdAndCreatedAtAfter(propertyId, since);
    }
}
