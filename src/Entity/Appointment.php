<?php

namespace App\Entity;

use App\Repository\AppointmentRepository;
use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Context\ExecutionContextInterface;

#[ORM\Entity(repositoryClass: AppointmentRepository::class)]
class Appointment
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'appointmentId')]
    private ?int $appointmentId = null; 

    #[ORM\Column(type: Types::DATE_MUTABLE)]
    #[Assert\GreaterThan("today", message: "You cannot make an appointment in the past.")]
    private ?\DateTimeInterface $Appointment_Date = null;
    #[ORM\Column(type: Types::TIME_MUTABLE)]
    private ?\DateTimeInterface $Appointment_Time = null;

    #[ORM\Column(length: 255)]
    private ?string $Appointment_Status = 'pending';

    #[ORM\OneToOne(mappedBy: 'Appointment_Key', cascade: ['persist', 'remove'])]
    private ?Rapport $rapport = null;

    #[ORM\ManyToOne(inversedBy: 'appointments')]
    #[ORM\JoinColumn(nullable: false, name: 'Account_Key', referencedColumnName: 'accountId')]
    #[Assert\NotNull(message: "Please select an account!!.")]
    private ?Account $Account_Key = null;

    #[ORM\ManyToOne(inversedBy: 'appointments')]
    #[ORM\JoinColumn(nullable: false, name: 'Animal_Key', referencedColumnName: 'animalId')]
    #[Assert\NotBlank(message:"Please select an animal.")]

    
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
 
    #[Assert\Callback]
    public function validateTime(ExecutionContextInterface $context, $payload): void
{
    $appointmentTime = $this->getAppointmentTime();
    if ($appointmentTime instanceof \DateTimeInterface) {
        // Extract hours and minutes from the appointment time
        $appointmentHour = (int) $appointmentTime->format('G');
        $appointmentMinute = (int) $appointmentTime->format('i');

        // Define earliest and latest allowed hours and minutes
        $earliestHour = 8; // 8 AM
        $latestHour = 19; // 7 PM

        // Check if the appointment time is before 8 AM or after 7 PM
        if ($appointmentHour < $earliestHour || ($appointmentHour === $latestHour && $appointmentMinute > 0) || $appointmentHour > $latestHour) {
            $context->buildViolation('You cannot make an appointment before 8am or past 7pm.')
                    ->atPath('Appointment_Time')
                    ->addViolation();
        }
    }
}
//chatgptcore//






}