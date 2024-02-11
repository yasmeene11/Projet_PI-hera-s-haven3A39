<?php

namespace App\Entity;

use App\Repository\AppointmentRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: AppointmentRepository::class)]
class Appointment
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'appointmentId')]
    private ?int $appointmentId = null;

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    private ?\DateTimeInterface $Appointment_Date = null;

    #[ORM\Column(type: Types::TIME_MUTABLE)]
    private ?\DateTimeInterface $Appointment_Time = null;

    #[ORM\Column(length: 255)]
    private ?string $Appointment_Status = null;

    #[ORM\OneToOne(mappedBy: 'Appointment_Key', cascade: ['persist', 'remove'])]
    private ?Rapport $rapport = null;

    #[ORM\ManyToOne(inversedBy: 'appointments')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    private ?Account $Account_Key = null;

    #[ORM\ManyToOne(inversedBy: 'appointments')]
    #[ORM\JoinColumn(nullable: false, name: 'Animal_Key', referencedColumnName: 'animalId')]
    private ?Animal $Animal_Key = null;

    public function getAppointmentId(): ?int
    {
        return $this->appointmentId;
    }

    public function getAppointmentDate(): ?\DateTimeInterface
    {
        return $this->Appointment_Date;
    }

    public function setAppointmentDate(\DateTimeInterface $Appointment_Date): static
    {
        $this->Appointment_Date = $Appointment_Date;

        return $this;
    }

    public function getAppointmentTime(): ?\DateTimeInterface
    {
        return $this->Appointment_Time;
    }

    public function setAppointmentTime(\DateTimeInterface $Appointment_Time): static
    {
        $this->Appointment_Time = $Appointment_Time;

        return $this;
    }

    public function getAppointmentStatus(): ?string
    {
        return $this->Appointment_Status;
    }

    public function setAppointmentStatus(string $Appointment_Status): static
    {
        $this->Appointment_Status = $Appointment_Status;

        return $this;
    }

    public function getRapport(): ?Rapport
    {
        return $this->rapport;
    }

    public function setRapport(Rapport $rapport): static
    {
        if ($rapport->getAppointmentKey() !== $this) {
            $rapport->setAppointmentKey($this);
        }

        $this->rapport = $rapport;

        return $this;
    }

    public function getAccountKey(): ?Account
    {
        return $this->Account_Key;
    }

    public function setAccountKey(?Account $Account_Key): static
    {
        $this->Account_Key = $Account_Key;

        return $this;
    }

    public function getAnimalKey(): ?Animal
    {
        return $this->Animal_Key;
    }

    public function setAnimalKey(?Animal $Animal_Key): static
    {
        $this->Animal_Key = $Animal_Key;

        return $this;
    }
}
