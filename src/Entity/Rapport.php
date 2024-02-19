<?php

namespace App\Entity;
    use Symfony\Component\Validator\Constraints as Assert;
use App\Repository\RapportRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraint;

#[ORM\Entity(repositoryClass: RapportRepository::class)]
class Rapport
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'rapportId')]
    private ?int $rapportId = null;

    #[ORM\Column(length: 1000)]
    private ?string $Description = null;

    #[ORM\OneToOne(inversedBy: 'rapport', cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(nullable: false, name: 'Appointment_Key', referencedColumnName: 'appointmentId')]
    #[Assert\NotNull(message: "You must choose an appointment! if the list is empty please wait until a new appointment is created and try again! ")]

    private ?Appointment $Appointment_Key = null;

    public function getRapportId(): ?int
    {
        return $this->rapportId;
    }

    public function getDescription(): ?string
    {
        return $this->Description;
    }

    public function setDescription(string $Description): static
    {
        $this->Description = $Description;

        return $this;
    }

    public function getAppointmentKey(): ?Appointment
    {
        return $this->Appointment_Key;
    }

    public function setAppointmentKey(Appointment $Appointment_Key): static
    {
        $this->Appointment_Key = $Appointment_Key;

        return $this;
    }
    
}
