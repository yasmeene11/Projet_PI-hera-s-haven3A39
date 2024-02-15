<?php

namespace App\Form;

use App\Entity\Adoption;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use App\Entity\Account;
use App\Entity\Animal;
class AdoptionType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        // Use condition to include/exclude fields based on the context
        if ($options['is_admin']) {
            // Admin has access to all fields
            $builder
                ->add('Adoption_Date')
                ->add('Adoption_Status', ChoiceType::class, [
                    'choices' => [
                        'Cancelled' => 'Cancelled',       
                'Pending' => 'Pending',
                'Adopted' => 'Adopted',
                    ],
                    'placeholder' => 'Select Status',
                    'required' => true,
                ])
                ->add('Adoption_Fee')
                ->add('Account_Key', EntityType::class, [
                    'class' => Account::class, 
                    'choice_label' => 'name', // Replace with the actual property name
                    'placeholder' => 'Select Account',
                    'required' => true,
                ])
                ->add('Animal_Key', EntityType::class, [
                    'class' => Animal::class, 
                    'choice_label' => 'Animal_Image', // Replace with the actual property name
                    'placeholder' => 'Select Animal',
                    'required' => true,
                ]);
        } else {
            // Front-end user can only select the date
            $builder->add('Adoption_Date');
        }
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Adoption::class,
            // Add a custom option to pass the role/context information
            'is_admin' => false,
        ]);
    }
}
