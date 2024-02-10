<?php

namespace App\Entity;

use App\Repository\RapportRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: RapportRepository::class)]
class Rapport
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $rapportId = null;

    #[ORM\Column(length: 1000)]
    private ?string $Description = null;

    #[ORM\OneToOne(inversedBy: 'rapport', cascade: ['persist', 'remove'])]
    #[ORM\JoinColumn(nullable: false)]
    #[ORM\JoinColumn(name: 'Appointment_Key', referencedColumnName:'appointmentId')]
    private ?Appointment $Appointment_Key = null;

    public function getRapportId(): ?int
    {
        return $this->RapportId;
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
